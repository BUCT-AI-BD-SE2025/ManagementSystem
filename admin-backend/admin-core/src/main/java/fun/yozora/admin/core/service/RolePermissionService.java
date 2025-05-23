package fun.yozora.admin.core.service;

import cn.dev33.satoken.util.SaResult;
import fun.yozora.admin.domain.entity.RolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author Yozor
* @description 针对表【role_permission】的数据库操作Service
* @createDate 2025-05-21 22:40:49
*/
public interface RolePermissionService extends IService<RolePermission> {


    boolean assignPermissionsToRole(String roleId, List<String> permIds);
}
