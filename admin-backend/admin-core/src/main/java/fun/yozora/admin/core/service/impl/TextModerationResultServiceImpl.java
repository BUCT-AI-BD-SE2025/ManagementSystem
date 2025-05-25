package fun.yozora.admin.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.yozora.admin.domain.entity.TextModerationResult;
import fun.yozora.admin.core.service.TextModerationResultService;
import fun.yozora.admin.repository.mapper.TextModerationResultMapper;
import org.springframework.stereotype.Service;

/**
* @author Yozor
* @description 针对表【text_moderation_result】的数据库操作Service实现
* @createDate 2025-05-22 22:21:44
*/
@Service
public class TextModerationResultServiceImpl extends ServiceImpl<TextModerationResultMapper, TextModerationResult>
    implements TextModerationResultService{

}




