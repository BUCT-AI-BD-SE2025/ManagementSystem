package com.buct_ai_bd_se2025.managementsystem.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.buct_ai_bd_se2025.managementsystem.dto.UpdateUserInfoDTO;
import com.buct_ai_bd_se2025.managementsystem.dto.UserInfoDTO;
import com.buct_ai_bd_se2025.managementsystem.entity.Permission;
import com.buct_ai_bd_se2025.managementsystem.entity.User;
import com.buct_ai_bd_se2025.managementsystem.enums.UserStatus;
import com.buct_ai_bd_se2025.managementsystem.mapper.UserMapper;
import com.buct_ai_bd_se2025.managementsystem.service.PermissionService;
import com.buct_ai_bd_se2025.managementsystem.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.stylesheets.LinkStyle;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController
{
    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
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
        if (UserStatus.LOCKED.equals(dbUser.getStatus()))
        {
            return SaResult.error("已被封禁");
        }
        if (UserStatus.DELETED.equals(dbUser.getStatus()))
        {
            return SaResult.error("用户已被删除");
        }
        if (UserStatus.ACTIVE.equals(dbUser.getStatus()))
        {

            dbUser.setLastLogin(LocalDateTime.now());
            dbUser.setIp(request.getRemoteAddr());

            userService.updateIp(dbUser);
            userService.updateLastLogin(dbUser);

            StpUtil.login(dbUser.getUid());
            return SaResult.ok("登录成功");
        }

        return SaResult.error("未知错误");
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public SaResult doRegister(String username, String password, String email)
    {
        if (username==null||password==null||email==null)
        {
            return SaResult.error("用户名或密码或邮箱不能为空");
        }

        if (userService.isExistUsername(username))
            return SaResult.error("用户名已存在");

        if (userService.isExistEmail(email))
            return SaResult.error("邮箱已存在");

        User user = new User(username,  password, email);

        if (userService.addUser(user))
            return SaResult.ok("注册成功");
        else
            return SaResult.error("注册失败");
    }
    @RequestMapping(value = "/logout",method = RequestMethod.DELETE)
    public SaResult doLogout()
    {
        StpUtil.logout();
        return SaResult.ok("登出成功");
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public SaResult update(@RequestBody UpdateUserInfoDTO user)
    {
        if (!StpUtil.isLogin())
        {
            return SaResult.error("未登录");
        }

        String uid = StpUtil.getLoginId().toString();
        User dbUser = userService.getUserByUid(uid);

        if (dbUser == null)
        {
            return SaResult.error("用户不存在");
        }

        BeanUtils.copyProperties(user, dbUser);

        if (userService.updateUser(dbUser))
        {
            return SaResult.ok("更新成功");
        }
        else
        {
            return SaResult.error("更新失败");
        }
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

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public SaResult getUserInfo()
    {
        if (!StpUtil.isLogin())
        {
            return SaResult.error("未登录");
        }
        String uid = StpUtil.getLoginId().toString();
        User user = userService.getUserByUid(uid);

        if (user == null)
        {
            return SaResult.error("用户不存在");
        }
        UserInfoDTO userInfoDTO = new UserInfoDTO(user);
        return SaResult.data(userInfoDTO);
    }
}
