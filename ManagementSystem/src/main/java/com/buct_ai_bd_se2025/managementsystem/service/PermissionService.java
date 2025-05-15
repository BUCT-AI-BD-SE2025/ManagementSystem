package com.buct_ai_bd_se2025.managementsystem.service;

import com.buct_ai_bd_se2025.managementsystem.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author YOZORA
* @description 针对表【permission】的数据库操作Service
* @createDate 2025-05-12 00:32:54
*/
public interface PermissionService extends IService<Permission> {
    List<Permission> getAllPermissionsByUserId(String uid);
}
