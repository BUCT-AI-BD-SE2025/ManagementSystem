package com.buct_ai_bd_se2025.managementsystem.service;

import com.buct_ai_bd_se2025.managementsystem.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author YOZORA
* @description 针对表【user】的数据库操作Service
* @createDate 2025-05-12 00:32:16
*/
public interface UserService extends IService<User> {
    User varifyUser(User user);

    User getUserByUsername(String username);

}
