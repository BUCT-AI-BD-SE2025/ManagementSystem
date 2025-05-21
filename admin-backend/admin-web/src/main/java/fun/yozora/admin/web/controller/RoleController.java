package fun.yozora.admin.web.controller;

import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.yozora.admin.core.service.RoleService;
import fun.yozora.admin.domain.entity.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController
{

    @Autowired
    private RoleService roleService;

    @GetMapping
    public SaResult getRoles(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(required = false) String roleId,
            @RequestParam(required = false) String roleCode,
            @RequestParam(required = false) String roleName,
            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "false") boolean isAll
    ){
        if (isAll)
            return SaResult.data(roleService.list());
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if (roleId != null && !roleId.isEmpty())
            queryWrapper.eq("role_id", roleId);
        if (roleCode != null && !roleCode.isEmpty())
            queryWrapper.eq("roleCode", roleCode);
        if (roleName != null && !roleName.isEmpty())
            queryWrapper.like("role_name", roleName);
        if (description != null && !description.isEmpty())
            queryWrapper.like("description", description);
        Page<Role> rolePage = roleService.page(new Page<>(page, pageSize), queryWrapper);
        return SaResult.data(rolePage);
    }
    @GetMapping("/{roleId}")
    public SaResult getRoleById(@PathVariable Integer roleId)
    {
        Role role = roleService.getById(roleId);
        return role != null ? SaResult.data(role) : SaResult.error("查询失败");
    }
    @PutMapping("/{roleId}")
    public SaResult updateRole(@PathVariable String roleId, @RequestBody Role role)
    {
        role.setRoleId(roleId);
        boolean update = roleService.updateById(role);
        return update ? SaResult.ok("更新成功") : SaResult.error("更新失败");
    }
    @PostMapping
    public SaResult createRole(@RequestBody Role role)
    {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("roleCode", role.getRoleCode());
        boolean exists = roleService.exists(wrapper);

        if (exists) {
            return SaResult.error("角色编码已存在，请更换");
        }
        boolean save = roleService.save(role);
        return save ? SaResult.ok("创建成功") : SaResult.error("创建失败");
    }
    @DeleteMapping("/{roleId}")
    public SaResult deleteRole(@PathVariable String roleId)
    {
        return SaResult.data(roleService.removeById(roleId));
    }
    @DeleteMapping("/batch")
    public SaResult deleteRoles(@RequestBody List<String> ids)
    {
        return SaResult.data(roleService.removeByIds(ids));
    }
};



