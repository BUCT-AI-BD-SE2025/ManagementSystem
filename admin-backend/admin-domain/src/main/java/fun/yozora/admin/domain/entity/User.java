package fun.yozora.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

import fun.yozora.admin.domain.enums.UserStatus;
import lombok.Data;
import com.fasterxml.uuid.Generators;
/**
 * 
 * &#064;TableName  user
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
    private UserStatus status;

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

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    public User(String username, String password, String email)
    {
        this.uid = Generators.timeBasedGenerator().generate().toString(); // UUID v7
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = "";
        this.sex = "2";
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}