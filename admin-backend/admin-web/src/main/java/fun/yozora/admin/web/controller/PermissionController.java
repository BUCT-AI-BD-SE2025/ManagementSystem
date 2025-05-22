package fun.yozora.admin.web.controller;

import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.yozora.admin.core.annotation.LogOperation;
import fun.yozora.admin.core.service.PermissionService;
import fun.yozora.admin.domain.entity.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/permissions")
public class PermissionController
{

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    public SaResult getPermissions(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "20") Integer pageSize,
            @RequestParam(required = false) String permId,
            @RequestParam(required = false) String permCode,
            @RequestParam(required = false) String permName,
            @RequestParam(required = false) String description,
            @RequestParam(defaultValue = "false") boolean isAll
    ){
        if (isAll)
            return SaResult.data(permissionService.list());
        QueryWrapper<Permission> queryWrapper = new QueryWrapper<>();
        if (permId != null && !permId.isEmpty())
            queryWrapper.eq("perm_id", permId);
        if (permCode != null && !permCode.isEmpty())
            queryWrapper.eq("perm_code", permCode);
        if (permName != null && !permName.isEmpty())
            queryWrapper.like("perm_name", permName);
        if (description != null && !description.isEmpty())
            queryWrapper.like("description", description);
        Page<Permission> permissionPage = permissionService.page(new Page<>(page, pageSize), queryWrapper);
        return SaResult.data(permissionPage);
    }
    @GetMapping("/{permId}")
    public SaResult getPermissionById(@PathVariable Integer permId)
    {
        Permission permission = permissionService.getById(permId);
        return permission != null ? SaResult.data(permission) : SaResult.error("查询失败");
    }
    @LogOperation(targetType = "permission", actionType = "update")
    @PutMapping("/{permId}")
    public SaResult updatePermission(@PathVariable String permId, @RequestBody Permission permission)
    {
        permission.setPermId(permId);
        boolean update = permissionService.updateById(permission);
        return update ? SaResult.ok("更新成功") : SaResult.error("更新失败");
    }
    @LogOperation(targetType = "permission", actionType = "create")
    @PostMapping
    public SaResult createPermission(@RequestBody Permission permission)
    {
        QueryWrapper<Permission> wrapper = new QueryWrapper<>();
        wrapper.eq("perm_code", permission.getPermCode());
        boolean exists = permissionService.exists(wrapper);

        if (exists) {
            return SaResult.error("权限编码已存在，请更换");
        }
        boolean save = permissionService.save(permission);
        return save ? SaResult.ok("创建成功") : SaResult.error("创建失败");
    }
    @LogOperation(targetType = "permission", actionType = "delete")
    @DeleteMapping("/{permId}")
    public SaResult deletePermission(@PathVariable String permId)
    {
        return SaResult.data(permissionService.removeById(permId));
    }
    @LogOperation(targetType = "permission", actionType = "delete")
    @DeleteMapping("/batch")
    public SaResult deletePermissions(@RequestBody List<String> ids)
    {
        return SaResult.data(permissionService.removeByIds(ids));
    }


}

