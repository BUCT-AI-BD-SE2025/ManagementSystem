package fun.yozora.admin.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.yozora.admin.domain.entity.Permission;
import fun.yozora.admin.domain.entity.Role;
import fun.yozora.admin.repository.mapper.PermissionMapper;
import fun.yozora.admin.core.service.PermissionService;
import fun.yozora.admin.core.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author YOZORA
* @description 针对表【permission】的数据库操作Service实现
* @createDate 2025-05-12 00:32:54
*/
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionMapper rolePermissionMapper;

    @Override
     public List<Permission> getAllPermissionsByUserId(String userId) {
        List<Role> roles = roleService.getRolesByUserId(userId);
        if (roles.isEmpty()) return List.of();

        return roles.stream()
                .flatMap(role -> rolePermissionMapper.selectPermissionsByRoleId(role.getRoleId()).stream())
                .distinct()
                .toList();
    }




}




