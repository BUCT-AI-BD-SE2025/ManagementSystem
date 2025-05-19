package fun.yozora.admin.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import fun.yozora.admin.domain.entity.Role;

import java.util.List;

/**
* @author YOZORA
* @description 针对表【role】的数据库操作Service
* @createDate 2025-05-12 00:32:38
*/
public interface RoleService extends IService<Role> {
    List<Role> getRolesByUserId(String id);
}
