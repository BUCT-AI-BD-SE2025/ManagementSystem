package com.buct_ai_bd_se2025.managementsystem.controller.v1;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.buct_ai_bd_se2025.managementsystem.entity.User;
import com.buct_ai_bd_se2025.managementsystem.enums.UserStatus;
import com.buct_ai_bd_se2025.managementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    @Autowired
    private UserService userService;

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


}
