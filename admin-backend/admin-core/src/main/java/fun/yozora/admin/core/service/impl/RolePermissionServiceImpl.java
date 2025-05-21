package fun.yozora.admin.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.yozora.admin.domain.entity.RolePermission;
import fun.yozora.admin.core.service.RolePermissionService;
import fun.yozora.admin.repository.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;

/**
* @author Yozor
* @description 针对表【role_permission】的数据库操作Service实现
* @createDate 2025-05-21 22:40:49
*/
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission>
    implements RolePermissionService{

}




