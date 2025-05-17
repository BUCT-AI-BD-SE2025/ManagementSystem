package fun.yozora.admin.core.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.yozora.admin.core.entity.Permission;
import org.mapstruct.Mapper;

import java.util.List;

/**
* @author YOZORA
* @description 针对表【permission】的数据库操作Mapper
* @createDate 2025-05-12 00:32:54
* @Entity fun.yozora.admin.core.entity.Permission
*/

public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> selectPermissionsByRoleId(String roleId);
}




