package com.buct_ai_bd_se2025.managementsystem.dto;

import com.buct_ai_bd_se2025.managementsystem.entity.Role;
import com.buct_ai_bd_se2025.managementsystem.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class LoginUserDto {

    @Data
    public static class UserInfo {
        private String uid;
        private String username;
        private List<String> roles;
        private List<String> perms;

        public UserInfo(User user, List<Role> roles, List<String> perms) {
            this.uid = user.getUid();
            this.username = user.getUsername();
            this.roles = roles.stream().map(Role::getRoleCode).toList();
            this.perms =  perms;
        }
    }

}