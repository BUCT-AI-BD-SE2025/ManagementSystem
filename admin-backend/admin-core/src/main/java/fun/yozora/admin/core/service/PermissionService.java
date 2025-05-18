package fun.yozora.admin.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import fun.yozora.admin.domain.entity.Permission;

import java.util.List;

/**
* @author YOZORA
* @description 针对表【permission】的数据库操作Service
* @createDate 2025-05-12 00:32:54
*/
public interface PermissionService extends IService<Permission> {
    List<Permission> getAllPermissionsByUserId(String uid);
}
