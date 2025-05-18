package fun.yozora.admin.repository.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.yozora.admin.domain.entity.Permission;

import java.util.List;

public interface RolePermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据角色ID查询所有权限
     */
    List<Permission> selectPermissionsByRoleId(String roleId);
}

