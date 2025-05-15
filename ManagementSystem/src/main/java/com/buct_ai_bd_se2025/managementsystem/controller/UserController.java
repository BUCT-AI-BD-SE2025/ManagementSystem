package com.buct_ai_bd_se2025.managementsystem.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.buct_ai_bd_se2025.managementsystem.entity.Permission;
import com.buct_ai_bd_se2025.managementsystem.entity.User;
import com.buct_ai_bd_se2025.managementsystem.mapper.UserMapper;
import com.buct_ai_bd_se2025.managementsystem.service.PermissionService;
import com.buct_ai_bd_se2025.managementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.stylesheets.LinkStyle;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController
{
    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/dologin", method = RequestMethod.POST)
    public SaResult doLogin(String username, String password)
    {
        if (username==null||password==null)
        {
            return SaResult.error("用户名或密码不能为空");
        }
        User dbUser = userService.getUserByUsername(username);
        if (dbUser == null)
        {
            return SaResult.error("用户不存在");
        }
        if (!dbUser.getPassword().equals(password))
        {
            return SaResult.error("密码错误");
        }
        else
        {
            StpUtil.login(dbUser.getUid());
            return SaResult.ok("登录成功");
        }
    }

    @RequestMapping(value = "/logout",method = RequestMethod.DELETE)
    public SaResult doLogout()
    {
        StpUtil.logout();
        return SaResult.ok("登出成功");
    }

    @RequestMapping("/isLogin")
    public SaResult isLogin()
    {
        if (StpUtil.isLogin())
        {
            return SaResult.ok("已登录");
        }
        else
        {
            return SaResult.error("未登录");
        }
    }

    @RequestMapping("/tokenInfo")
    public SaResult tokenInfo()
    {
        return SaResult.data(StpUtil.getTokenInfo());
    }

    @RequestMapping("/permission")
    public SaResult permission()
    {
        System.out.println(StpUtil.getPermissionList());
        return SaResult.data(StpUtil.getPermissionList());
    }

    @RequestMapping("/role")
    public SaResult role()
    {
        return SaResult.data(StpUtil.getRoleList());
    }

    //uid
    @RequestMapping("/uid")
    public SaResult uid()
    {
        return SaResult.data(StpUtil.getLoginId());
    }
    @RequestMapping(value = "/test",method = RequestMethod.GET)
    public String test()
    {
        List<Permission> permissionList = permissionService.getAllPermissionsByUserId("0");
        System.out.println(permissionList.stream().map(Permission::getPermName).toList());
        return "OK";
    }
}
