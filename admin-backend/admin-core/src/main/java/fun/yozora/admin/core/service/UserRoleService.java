package fun.yozora.admin.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import fun.yozora.admin.domain.entity.UserRole;

import java.util.List;

public interface UserRoleService extends IService<UserRole> {
    boolean assignRoles(String userId, List<String> roleCodes);
}
