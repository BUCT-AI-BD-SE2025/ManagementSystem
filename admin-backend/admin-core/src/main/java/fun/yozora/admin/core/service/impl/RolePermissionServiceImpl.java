package fun.yozora.admin.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.yozora.admin.domain.entity.RolePermission;
import fun.yozora.admin.core.service.RolePermissionService;
import fun.yozora.admin.repository.mapper.RolePermissionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author Yozor
* @description 针对表【role_permission】的数据库操作Service实现
* @createDate 2025-05-21 22:40:49
*/
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
    implements RolePermissionService{

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Override
    public boolean assignPermissionsToRole(String roleId, List<String> permIds) {

        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id", roleId);
        rolePermissionMapper.delete(wrapper);
        List<RolePermission> list = new ArrayList<>();
        for (String permId : permIds) {
            RolePermission rp = new RolePermission();
            rp.setRoleId(roleId);
            rp.setPermId(permId);
            list.add(rp);
        }
        rolePermissionMapper.insert(list);
        return true;
    }
}




