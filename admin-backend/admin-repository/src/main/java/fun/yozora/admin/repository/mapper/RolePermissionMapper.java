package fun.yozora.admin.repository.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.yozora.admin.domain.entity.Permission;
import fun.yozora.admin.domain.entity.RolePermission;

import java.util.List;

public interface RolePermissionMapper extends BaseMapper<RolePermission> {

    /**
     * 根据角色ID查询所有权限
     */
    List<Permission> selectPermissionsByRoleId(String roleId);
}

