package com.buct_ai_bd_se2025.managementsystem.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import com.buct_ai_bd_se2025.managementsystem.entity.Permission;
import com.buct_ai_bd_se2025.managementsystem.entity.Role;
import com.buct_ai_bd_se2025.managementsystem.service.PermissionService;
import com.buct_ai_bd_se2025.managementsystem.service.RoleService;
import com.buct_ai_bd_se2025.managementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StpInterfaceImpl implements StpInterface
{
    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType)
    {
        return permissionService.getAllPermissionsByUserId(loginId.toString()).stream().map(Permission::getPermName).toList();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType)
    {
        return roleService.getRolesByUserId(loginId.toString()).stream().map(Role::getRoleName).toList();
    }
}
