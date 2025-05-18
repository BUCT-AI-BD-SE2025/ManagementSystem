package fun.yozora.admin.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.yozora.admin.domain.entity.Role;
import fun.yozora.admin.domain.entity.UserRole;

import java.util.List;

/**
 * @author YOZORA
 * @description 针对表【role】的数据库操作Mapper
 * @createDate 2025-05-12 00:32:38
 */

public interface UserRoleMapper extends BaseMapper<UserRole>
{
    List<Role> selectRolesByUserId(String uid);
}
