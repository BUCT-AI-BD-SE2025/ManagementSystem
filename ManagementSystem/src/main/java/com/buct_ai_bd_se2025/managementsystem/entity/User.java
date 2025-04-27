package com.buct_ai_bd_se2025.managementsystem.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;

@Entity
@Table(name = "user",
        indexes = {
                @Index(name = "idx_username", columnList = "username"),
                @Index(name = "idx_email", columnList = "email"),
                @Index(name = "idx_status", columnList = "status")
        }
)
@Data
public class User {

    @Id
    private Long id;

    private String username;

    private String email;

    private String password;

    private String nickname = "";

    private String avatar;

    private Boolean status = true;

    private Boolean isLocked = false;

    private Boolean isEmailVerified = false;

    private LocalDateTime registerTime;

    private LocalDateTime lastLoginTime;

    private LocalDateTime updateTime;
}