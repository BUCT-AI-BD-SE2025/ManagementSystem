package fun.yozora.admin.web.dto;

import lombok.Data;

@Data
public class UpdateUserInfoDTO {
    private String nickname;
    private String sex;
    private String avatarUrl;
    private String phone;
    private String email;
    private String password;
}
