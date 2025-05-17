package fun.yozora.admin.core.dto;

import cn.dev33.satoken.stp.StpUtil;
import fun.yozora.admin.core.entity.User;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserInfoDTO {
    private String uid;
    private String username;
    private String nickname;
    private String email;
    private String phone;
    private String sex;
    private String avatarUrl;
    private Object status;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;
    private List<String> roles;
    private List<String> permissions;
    private String realName;

    public UserInfoDTO(User user) {
        this.uid = user.getUid();
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.sex = user.getSex();
        this.avatarUrl = user.getAvatarUrl();
        this.status = user.getStatus();
        this.lastLogin = user.getLastLogin();
        this.createdAt = user.getCreatedAt();
        this.realName = user.getUsername();
    }
}
