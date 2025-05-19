package fun.yozora.admin.web.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import fun.yozora.admin.web.dto.LoginDTO;
import fun.yozora.admin.domain.entity.User;
import fun.yozora.admin.domain.enums.UserStatus;
import fun.yozora.admin.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public SaResult Login(@RequestBody LoginDTO loginDTO)
    {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        if (!loginDTO.isCaptcha())
        {
            return SaResult.error("验证错误");
        }
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

            StpUtil.login(dbUser.getId());
            String token = StpUtil.getTokenValue();
            return SaResult.ok("登录成功").setData(Map.of("accessToken",token));
        }

        return SaResult.error("未知错误");
    }

    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public SaResult doLogout()
    {
        StpUtil.logout();
        return SaResult.ok("登出成功");
    }

    @RequestMapping(value = "/codes", method = RequestMethod.GET)
    public SaResult getCodes()
    {
        if (!StpUtil.isLogin())
            return SaResult.error("未登录");
        return SaResult.data(StpUtil.getPermissionList());
    }

    @RequestMapping(value = "refresh", method = RequestMethod.GET)
    public SaResult refresh()
    {
        if (!StpUtil.isLogin())
            return SaResult.error("未登录");

        String token = StpUtil.getTokenValue();

        return SaResult.ok("刷新成功").setData(Map.of("accessToken",token,"status", 200));
    }
}
