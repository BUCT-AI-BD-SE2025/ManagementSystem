package fun.yozora.admin.web.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.uuid.Generators;
import fun.yozora.admin.web.dto.UserInfoDTO;
import fun.yozora.admin.domain.entity.User;
import fun.yozora.admin.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController
{
    @Autowired
    private UserService userService;

    @PostMapping()
    public SaResult createUser(@RequestBody User user)
    {
        user.setUid(Generators.timeBasedGenerator().generate().toString());
        if (userService.save(user))
            return SaResult.ok("创建成功");
        else
            return SaResult.error("创建失败");
    }

    @GetMapping(value = "/all")
    public SaResult getUsers(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(required = false) String uid,
            @RequestParam(required = false) String username,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String sex,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime loginStartTime,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime loginEndTime
    )
    {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // UID精确匹配
        if(uid !=null&&!uid.isEmpty())
            queryWrapper.eq("uid", uid);
        // 用户名模糊匹配
        if(username !=null&&!username.isEmpty())
            queryWrapper.like("username", username);
        // 昵称模糊匹配
        if(nickname !=null&&!nickname.isEmpty())
            queryWrapper.like("nickname", nickname);
        // 邮箱模糊匹配
        if(email !=null&&!email.isEmpty())
            queryWrapper.like("email", email);
        // 手机号模糊匹配
        if(phone !=null&&!phone.isEmpty())
            queryWrapper.like("phone", phone);
        // 状态精确匹配
        if(status !=null&&!status.isEmpty())
            queryWrapper.eq("status", status);
        //  性别精确匹配
        if(sex !=null&&!sex.isEmpty())
            queryWrapper.eq("sex", sex);
        // 注册时间范围
        if(startTime !=null)
            queryWrapper.ge("created_at", startTime);
        if(endTime !=null)
            queryWrapper.le("created_at", endTime);
        // 登录时间范围
        if(loginStartTime !=null)
            queryWrapper.ge("last_login", loginStartTime);
        if(loginEndTime !=null)
            queryWrapper.le("last_login", loginEndTime);
        Page<User> userPage = userService.page(new Page<>(page, pageSize), queryWrapper);
        return SaResult.data(userPage);
    }

    @PutMapping(value = "/{uid}")
    public SaResult updateUser(@PathVariable String uid, @RequestBody User user)
    {
        user.setUid(uid);
        return SaResult.data(userService.updateById(user));
    }

    @DeleteMapping(value = "/{id}")
    public SaResult deleteUser(@PathVariable String id)
    {
        System.out.println(id);
        return SaResult.data(userService.removeById(id));
    }
    @DeleteMapping(value = "/batch")
    public SaResult deleteUsers(@RequestBody List<String> ids)
    {
        return SaResult.data(userService.removeBatchByIds(ids));
    }

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public SaResult doRegister(String username, String password, String email)
    {
        if (username==null||password==null||email==null)
            return SaResult.error("用户名或密码或邮箱不能为空");

        if (userService.isExistUsername(username))
            return SaResult.error("用户名已存在");

        if (userService.isExistEmail(email))
            return SaResult.error("邮箱已存在");

        User user = new User(username,  password, email);

        if (userService.save(user))
            return SaResult.ok("注册成功");
        else
            return SaResult.error("注册失败");
    }


    @RequestMapping(value = "/isLogin",method = RequestMethod.GET)
    public SaResult isLogin()
    {
        if (StpUtil.isLogin())
            return SaResult.ok("已登录");
        else
            return SaResult.error("未登录").setCode(401);
    }

    @RequestMapping(value = "/tokenInfo",method = RequestMethod.GET)
    public SaResult tokenInfo()
    {
        return SaResult.data(StpUtil.getTokenInfo());
    }


    @RequestMapping(value = "/permission", method = RequestMethod.GET)
    public SaResult permission()
    {
        return SaResult.data(StpUtil.getPermissionList());
    }


    @RequestMapping(value = "/role", method = RequestMethod.GET)
    public SaResult role()
    {
        return SaResult.data(StpUtil.getRoleList());
    }

    @RequestMapping(value = "/uid", method = RequestMethod.GET)
    public SaResult uid()
    {
        return SaResult.data(StpUtil.getLoginId());
    }
    
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public SaResult getUserInfo()
    {
        if (!StpUtil.isLogin())
            return SaResult.error("未登录").setCode(401);
        String id = StpUtil.getLoginId().toString();
        User user = userService.getUserByUid(id);

        if (user == null)
            return SaResult.error("用户不存在");
        UserInfoDTO userInfoDTO = new UserInfoDTO(user);

        userInfoDTO.setPermissions(StpUtil.getRoleList());
        userInfoDTO.setRoles(StpUtil.getRoleList());
        return SaResult.data(userInfoDTO);
    }
}
