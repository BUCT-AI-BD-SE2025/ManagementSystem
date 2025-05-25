package fun.yozora.admin.core.service;

import fun.yozora.admin.domain.dto.UserDTO;
import fun.yozora.admin.domain.entity.Comment;

public interface TextModerationService {
    String commentModeration(UserDTO user, Comment content);
}
