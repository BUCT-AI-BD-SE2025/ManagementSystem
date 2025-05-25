package fun.yozora.admin.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.yozora.admin.domain.entity.CommentReviewResult;
import fun.yozora.admin.core.service.CommentReviewResultService;
import fun.yozora.admin.repository.mapper.CommentReviewResultMapper;
import org.springframework.stereotype.Service;

/**
* @author Yozor
* @description 针对表【comment_review_result(人工审核表)】的数据库操作Service实现
* @createDate 2025-05-23 01:01:37
*/
@Service
public class CommentReviewResultServiceImpl extends ServiceImpl<CommentReviewResultMapper, CommentReviewResult>
    implements CommentReviewResultService{

}




