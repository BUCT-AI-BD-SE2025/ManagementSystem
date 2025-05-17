package com.buct_ai_bd_se2025.managementsystem.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private String username;
    private String password;
    private boolean captcha;
    private String accountType;
}
