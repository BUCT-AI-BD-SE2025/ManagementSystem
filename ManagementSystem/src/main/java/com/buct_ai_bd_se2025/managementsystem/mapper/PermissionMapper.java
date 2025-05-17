package com.buct_ai_bd_se2025.managementsystem.mapper;

import com.buct_ai_bd_se2025.managementsystem.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

import java.util.List;

/**
* @author YOZORA
* @description 针对表【permission】的数据库操作Mapper
* @createDate 2025-05-12 00:32:54
* @Entity com.buct_ai_bd_se2025.managementsystem.entity.Permission
*/

public interface PermissionMapper extends BaseMapper<Permission> {
    List<Permission> selectPermissionsByRoleId(String roleId);
}




