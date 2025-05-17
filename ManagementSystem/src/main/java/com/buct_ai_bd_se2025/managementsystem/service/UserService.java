package com.buct_ai_bd_se2025.managementsystem.service;

import com.buct_ai_bd_se2025.managementsystem.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author YOZORA
* @description 针对表【user】的数据库操作Service
* @createDate 2025-05-12 00:32:16
*/
public interface UserService extends IService<User> {
    User getUserByUsername(String username);

    boolean isExistUsername(String username);

    boolean isExistEmail(String email);

    boolean updateUser(User user);

    boolean deleteUser(User user);

    boolean updateLastLogin(User user);

    boolean updateIp(User user);

    boolean addUser(User user);

    User getUserByUid(String uid);
}
