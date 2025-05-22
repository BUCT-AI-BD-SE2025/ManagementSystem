package fun.yozora.admin.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.yozora.admin.domain.entity.Comment;
import fun.yozora.admin.core.service.CommentService;
import fun.yozora.admin.repository.mapper.CommentMapper;
import org.springframework.stereotype.Service;

/**
* @author YOZORA
* @description 针对表【comment】的数据库操作Service实现
* @createDate 2025-05-22 08:48:35
*/
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment>
    implements CommentService{

}




