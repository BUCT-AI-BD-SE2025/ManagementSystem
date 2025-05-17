package fun.yozora.admin.core.config;

import cn.dev33.satoken.stp.StpInterface;
import cn.dev33.satoken.stp.StpUtil;
import fun.yozora.admin.core.entity.Permission;
import fun.yozora.admin.core.entity.Role;
import fun.yozora.admin.core.service.PermissionService;
import fun.yozora.admin.core.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class StpInterfaceImpl implements StpInterface
{
    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType)
    {
        return permissionService.getAllPermissionsByUserId(loginId.toString()).stream().map(Permission::getPermName).toList();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType)
    {
        return roleService.getRolesByUserId(loginId.toString()).stream().map(Role::getRoleName).toList();
    }
}
