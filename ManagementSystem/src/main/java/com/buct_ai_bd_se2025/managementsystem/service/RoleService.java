package com.buct_ai_bd_se2025.managementsystem.service;

import com.buct_ai_bd_se2025.managementsystem.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author YOZORA
* @description 针对表【role】的数据库操作Service
* @createDate 2025-05-12 00:32:38
*/
public interface RoleService extends IService<Role> {
    List<Role> getRolesByUserId(String uid);
}
