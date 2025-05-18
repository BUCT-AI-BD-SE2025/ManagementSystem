package fun.yozora.admin.web.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String username;
    private String password;
    private boolean captcha;
    private String accountType;
}
