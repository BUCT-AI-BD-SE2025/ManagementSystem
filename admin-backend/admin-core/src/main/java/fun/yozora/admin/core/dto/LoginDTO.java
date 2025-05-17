package fun.yozora.admin.core.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String username;
    private String password;
    private boolean captcha;
    private String accountType;
}
