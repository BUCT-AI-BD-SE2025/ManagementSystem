package fun.yozora.admin.web.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.uuid.Generators;
import fun.yozora.admin.core.annotation.LogOperation;
import fun.yozora.admin.core.service.*;
import fun.yozora.admin.domain.dto.CommentPostDTO;
import fun.yozora.admin.domain.dto.ReviewResultDTO;
import fun.yozora.admin.domain.dto.UserDTO;
import fun.yozora.admin.domain.entity.Comment;
import fun.yozora.admin.domain.entity.CommentReviewResult;
import fun.yozora.admin.domain.entity.TextModerationResult;
import fun.yozora.admin.domain.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private TextModerationService textModerationService;

    @Autowired
    private UserService userService;
    @Autowired
    private TextModerationResultService textModerationResultService;

    @Autowired
    private CommentReviewResultService commentReviewResultService;

    private String getUUID(){
        return Generators.timeBasedGenerator().generate().toString();
    }

    @GetMapping
    public SaResult getComments(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(required = false) String commentId,
            @RequestParam(required = false) String userId,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime updateStartTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime updateEndTime
    )
    {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        if(commentId !=null&&!commentId.isEmpty())
            queryWrapper.eq("comment_id", commentId);
        if (userId !=null&&!userId.isEmpty())
            queryWrapper.eq("user_id", userId);
        if (content !=null&&!content.isEmpty())
            queryWrapper.like("content", content);
        if (status !=null&&!status.isEmpty())
            queryWrapper.eq("status", status);
        if (startTime !=null)
            queryWrapper.ge("created_time", startTime);
        if (endTime !=null)
            queryWrapper.le("created_time", endTime);
        if (updateStartTime !=null)
            queryWrapper.ge("updated_time", updateStartTime);
        if(updateEndTime !=null)
            queryWrapper.le("updated_time", updateEndTime);
        Page<Comment> commentPage = commentService.page(new Page<>(page, pageSize), queryWrapper);
        return SaResult.data(commentPage);
    }
    @GetMapping("/user/{userId}")
    public SaResult getCommentByUserId(
            @PathVariable String userId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize){
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);

        Page<Comment> commentPage = commentService.page(new Page<>(page, pageSize), queryWrapper);
        return SaResult.data(commentPage);
    }

    @LogOperation(targetType = "comment", actionType = "create")
    @GetMapping("/{commentId}")
    public SaResult getCommentById(@PathVariable String commentId) {
        return SaResult.data(commentService.getById(commentId));
    }

    @LogOperation(targetType = "comment", actionType = "create")
    @PostMapping
    public SaResult createComment(@RequestBody Comment comment) {
        comment.setCommentId(getUUID());
        return SaResult.data(commentService.save(comment));
    }

    @LogOperation(targetType = "comment", actionType = "update")
    @PutMapping("/{commentId}")
    public SaResult updateComment(@PathVariable String commentId, @RequestBody Comment comment) {
        comment.setCommentId(commentId);
        return SaResult.data(commentService.updateById(comment));
    }

    @LogOperation(targetType = "comment", actionType = "delete")
    @DeleteMapping("/{commentId}")
    public SaResult deleteComment(@PathVariable String commentId) {
        return SaResult.data(commentService.removeById(commentId));
    }

    @LogOperation(targetType = "comment", actionType = "delete")
    @DeleteMapping("/batch")
    public SaResult deleteComments(@RequestBody List<String> ids) {
        return SaResult.data(commentService.removeByIds(ids));
    }


    @LogOperation(targetType = "comment", actionType = "review")
    @PutMapping("/{commentId}/status")
    public SaResult updateCommentStatus(@PathVariable String commentId, @RequestBody ReviewResultDTO dto) {
        dto.getIds().add(commentId); // 把单个 ID 加入 list
        return updateCommentStatusBatch(dto);
    }


    @LogOperation(targetType = "comment", actionType = "review")
    @PutMapping("/status/batch")
    public SaResult updateCommentStatusBatch(@RequestBody ReviewResultDTO dto) {
        if (!StpUtil.isLogin())
            return SaResult.error("未登录").setCode(401);
        if (dto.getStatus() == null || dto.getStatus().isEmpty())
            return SaResult.error("状态不能为空");
        if (dto.getIds()  == null || dto.getIds().isEmpty())
            return SaResult.error("ID 不能为空");
        List<Comment> comments = commentService.listByIds(dto.getIds());

        for (Comment comment : comments) {
            comment.setStatus(dto.getStatus());
            CommentReviewResult commentReviewResult = new CommentReviewResult();
            commentReviewResult.setReviewId(getUUID());
            commentReviewResult.setCommentId(comment.getCommentId());
            commentReviewResult.setReviewerId(StpUtil.getLoginIdAsString());
            commentReviewResult.setReviewResult(dto.getStatus());
            commentReviewResult.setBlockReason(dto.getReason());
            commentReviewResultService.save(commentReviewResult);
        }
        return SaResult.data(commentService.updateBatchById(comments));
    }

    @SaCheckLogin
    @PostMapping("/post/{userId}")
    public SaResult postComment(@PathVariable String userId, @RequestBody CommentPostDTO dto) {
        Comment comment = new Comment();
        comment.setUserId(userId);
        comment.setContent(dto.getContent());
        comment.setCommentId(Generators.timeBasedGenerator().generate().toString());
        comment.setCreatedTime(LocalDateTime.now());
        comment.setUpdatedTime(LocalDateTime.now());

        UserDTO user = new UserDTO();
        User dbUser = userService.getUserByUid(userId);
        user.setUid(userId);
        user.setUsername(dbUser.getUsername());
        user.setNickname(dbUser.getNickname());

        comment.setStatus(textModerationService.commentModeration(user, comment));
        return SaResult.data(commentService.save(comment));
    }

    @GetMapping("result/api")
    public SaResult getCommentResultApi(@RequestParam String commentId) {
        if (commentService.getById(commentId) == null)
            return SaResult.error("评论不存在");
        return SaResult.data(textModerationResultService.list(new QueryWrapper<TextModerationResult>().eq("data_id", commentId)));
    }
    @GetMapping("result/review")
    public SaResult getCommentResultReview(@RequestParam String commentId) {
        if (commentService.getById(commentId) == null)
            return SaResult.error("评论不存在");
        return SaResult.data(commentReviewResultService.list(new QueryWrapper<CommentReviewResult>().eq("comment_id", commentId)));
    }

}


