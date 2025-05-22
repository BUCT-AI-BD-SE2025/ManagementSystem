package fun.yozora.admin.web.controller;

import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.uuid.Generators;
import fun.yozora.admin.core.annotation.LogOperation;
import fun.yozora.admin.core.service.CommentService;
import fun.yozora.admin.domain.entity.Comment;
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

    @LogOperation(targetType = "comment", actionType = "create")
    @GetMapping("/{commentId}")
    public SaResult getCommentById(@PathVariable String commentId) {
        return SaResult.data(commentService.getById(commentId));
    }

    @LogOperation(targetType = "comment", actionType = "create")
    @PostMapping
    public SaResult createComment(@RequestBody Comment comment) {
        comment.setCommentId(Generators.timeBasedGenerator().generate().toString());
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
}


