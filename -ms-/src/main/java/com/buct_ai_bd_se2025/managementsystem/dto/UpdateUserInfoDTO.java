package com.buct_ai_bd_se2025.managementsystem.dto;

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
