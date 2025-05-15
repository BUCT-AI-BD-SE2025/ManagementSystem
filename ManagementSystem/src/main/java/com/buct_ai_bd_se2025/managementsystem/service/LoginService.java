package com.buct_ai_bd_se2025.managementsystem.service;

import com.buct_ai_bd_se2025.managementsystem.entity.Permission;
import com.buct_ai_bd_se2025.managementsystem.entity.Role;
import com.buct_ai_bd_se2025.managementsystem.entity.User;

import java.util.List;


public interface LoginService
{
    User varifyUser(User user);

    String getToken(User user);

}
