package fun.yozora.admin.web.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import fun.yozora.admin.core.service.LoginLogService;
import fun.yozora.admin.domain.entity.LoginLog;
import fun.yozora.admin.web.dto.LoginDTO;
import fun.yozora.admin.domain.entity.User;
import fun.yozora.admin.domain.enums.UserStatus;
import fun.yozora.admin.core.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController
{
    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private LoginLogService loginLogService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public SaResult Login(@RequestBody LoginDTO loginDTO)
    {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();


        LoginLog loginLog = new LoginLog();

        loginLog.setUsername(username);
        loginLog.setIpAddress(request.getRemoteAddr());
        loginLog.setDeviceInfo(request.getHeader("User-Agent"));
        loginLog.setLoginTime(LocalDateTime.now());
        loginLog.setMessage("未知错误");
        loginLog.setIsSuccess(0);
        if (!loginDTO.isCaptcha())
        {
            loginLog.setMessage("验证未通过");
            loginLogService.save(loginLog);
            return SaResult.error("验证未通过");
        }
        if (username==null||password==null)
        {
            loginLog.setMessage("用户名或密码不能为空");
            loginLogService.save(loginLog);
            return SaResult.error("用户名或密码不能为空");
        }
        User dbUser = userService.getUserByUsername(username);
        if (dbUser == null)
        {
            loginLog.setMessage("用户不存在");
            loginLogService.save(loginLog);
            return SaResult.error("用户不存在");
        }
        loginLog.setUserId(dbUser.getUid());
        if (!dbUser.getPassword().equals(password))
        {
            loginLog.setMessage("密码错误");
            loginLogService.save(loginLog);
            return SaResult.error("密码错误");
        }
        if (UserStatus.LOCKED.equals(dbUser.getStatus()))
        {
            loginLog.setMessage("已被封禁");
            loginLogService.save(loginLog);
            return SaResult.error("已被封禁");
        }
        if (UserStatus.DELETED.equals(dbUser.getStatus()))
        {
            loginLog.setMessage("用户已被删除");
            loginLogService.save(loginLog);
            return SaResult.error("用户已被删除");
        }
        if (UserStatus.ACTIVE.equals(dbUser.getStatus()))
        {

            dbUser.setLastLogin(LocalDateTime.now());
            dbUser.setIp(request.getRemoteAddr());

            userService.updateIp(dbUser);
            userService.updateLastLogin(dbUser);

            StpUtil.login(dbUser.getUid());

            loginLog.setIsSuccess(1); // 成功
            loginLog.setMessage("登录成功");

            loginLogService.save(loginLog);
            String token = StpUtil.getTokenValue();
            return SaResult.ok("登录成功").setData(Map.of("accessToken",token));
        }
        loginLogService.save(loginLog);
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
