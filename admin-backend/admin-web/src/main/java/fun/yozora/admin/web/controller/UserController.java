package fun.yozora.admin.web.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.yozora.admin.web.dto.UpdateUserInfoDTO;
import fun.yozora.admin.web.dto.UserInfoDTO;
import fun.yozora.admin.domain.entity.User;
import fun.yozora.admin.core.service.PermissionService;
import fun.yozora.admin.core.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController
{
    @Autowired
    private UserService userService;

    @GetMapping(value = "/all")
    public SaResult getUsers(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize
            )
    {

        return SaResult.data(userService.page(new Page<>(page, pageSize)));
    }

    @DeleteMapping(value = "/{id}")
    public SaResult deleteUser(@PathVariable Integer id)
    {
        return SaResult.data(userService.removeById(id));
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


    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public SaResult update(@RequestBody UpdateUserInfoDTO user)
    {
        if (!StpUtil.isLogin())
            return SaResult.error("未登录").setCode(401);

        String uid = StpUtil.getLoginId().toString();
        User dbUser = userService.getUserByUid(uid);

        if (dbUser == null)
            return SaResult.error("用户不存在");

        BeanUtils.copyProperties(user, dbUser);

        if (userService.updateUser(dbUser))
            return SaResult.ok("更新成功");
        else
            return SaResult.error("更新失败");
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
        String uid = StpUtil.getLoginId().toString();
        User user = userService.getUserByUid(uid);

        if (user == null)
            return SaResult.error("用户不存在");
        UserInfoDTO userInfoDTO = new UserInfoDTO(user);

        userInfoDTO.setPermissions(StpUtil.getRoleList());
        userInfoDTO.setRoles(StpUtil.getRoleList());
        return SaResult.data(userInfoDTO);
    }
}
