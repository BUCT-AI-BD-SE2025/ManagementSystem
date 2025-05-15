package com.buct_ai_bd_se2025.managementsystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.buct_ai_bd_se2025.managementsystem.entity.Permission;
import com.buct_ai_bd_se2025.managementsystem.entity.Role;
import com.buct_ai_bd_se2025.managementsystem.entity.User;
import com.buct_ai_bd_se2025.managementsystem.mapper.RolePermissionMapper;
import com.buct_ai_bd_se2025.managementsystem.mapper.UserMapper;
import com.buct_ai_bd_se2025.managementsystem.mapper.UserRoleMapper;
import com.buct_ai_bd_se2025.managementsystem.service.LoginService;
import com.buct_ai_bd_se2025.managementsystem.service.PermissionService;
import com.buct_ai_bd_se2025.managementsystem.service.RoleService;
import com.buct_ai_bd_se2025.managementsystem.service.UserService;
import com.buct_ai_bd_se2025.managementsystem.utils.JWTUtils;
import com.buct_ai_bd_se2025.managementsystem.utils.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class LoginServiceImpl implements LoginService
{
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private UserMapper  userMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public User varifyUser(User user)
    {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername());
        User dbUser = userMapper.selectOne(queryWrapper);

        if (dbUser != null && PasswordUtil.checkPassword(user.getPassword(), dbUser.getPassword()))
        {
            return dbUser;
        }
        return null;
    }

    @Override
    public String getToken(User user)
    {
        Map<String, String> map = new HashMap<>();
        map.put("uid", user.getUid());
        map.put("username", user.getUsername());

        List<Role> roles = roleService.getRolesByUserId(user.getUid());
        if (!roles.isEmpty())
        {
            map.put("role", roles.stream().map(Role::getRoleCode).collect(Collectors.joining(",")));
        }

        List<Permission> permissions = permissionService.getAllPermissionsByUserId(user.getUid());
        if (!permissions.isEmpty())
            map.put("perms", permissions.stream()
                    .map(Permission::getPermCode)
                    .collect(Collectors.joining(",")));

        return JWTUtils.generateToken(map);
    }
}
