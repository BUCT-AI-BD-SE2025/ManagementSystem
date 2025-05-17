package com.buct_ai_bd_se2025.managementsystem.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName role
 */
@TableName(value ="role")
@Data
public class Role {
    /**
     * 
     */
    @TableId
    private String roleId;

    /**
     * 角色编码
     */
    private String roleCode;

    /**
     * 
     */
    private String roleName;

    /**
     * 
     */
    private String description;


}