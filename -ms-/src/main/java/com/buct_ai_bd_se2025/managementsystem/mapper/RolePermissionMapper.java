package com.buct_ai_bd_se2025.managementsystem.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.buct_ai_bd_se2025.managementsystem.entity.Permission;
import java.util.List;

public interface RolePermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据角色ID查询所有权限
     */
    List<Permission> selectPermissionsByRoleId(String roleId);
}

