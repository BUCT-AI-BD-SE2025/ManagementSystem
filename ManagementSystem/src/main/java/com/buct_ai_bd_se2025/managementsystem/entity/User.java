package com.buct_ai_bd_se2025.managementsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable
{
    /**
     * 
     */
    @TableId
    private String uid;

    /**
     * 登陆用用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * BCrypt加密
     */
    private String password;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 0男1女2未知
     */
    private String sex;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 图片URL
     */
    private String avatarUrl;

    /**
     * 
     */
    private Object status;

    /**
     * 
     */
    private LocalDateTime lastLogin;

    /**
     * 
     */
    private LocalDateTime createdAt;

    /**
     * 
     */
    private LocalDateTime updatedAt;


}