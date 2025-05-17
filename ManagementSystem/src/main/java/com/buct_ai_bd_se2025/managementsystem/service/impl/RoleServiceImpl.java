package com.buct_ai_bd_se2025.managementsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buct_ai_bd_se2025.managementsystem.entity.Role;
import com.buct_ai_bd_se2025.managementsystem.mapper.UserRoleMapper;
import com.buct_ai_bd_se2025.managementsystem.service.RoleService;
import com.buct_ai_bd_se2025.managementsystem.mapper.RoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author YOZORA
* @description 针对表【role】的数据库操作Service实现
* @createDate 2025-05-12 00:32:38
*/
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
    implements RoleService{

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Override
    public List<Role> getRolesByUserId(String uid)
    {
        return userRoleMapper.selectRolesByUserId(uid);
    }
}




