package com.buct_ai_bd_se2025.managementsystem.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.buct_ai_bd_se2025.managementsystem.entity.User;
import com.buct_ai_bd_se2025.managementsystem.service.UserService;
import com.buct_ai_bd_se2025.managementsystem.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author YOZORA
* @description 针对表【user】的数据库操作Service实现
* @createDate 2025-05-12 00:32:16
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public User varifyUser(User user)
    {

        return null;
    }

    @Override
    public User getUserByUsername(String username)
    {
        return baseMapper.selectByUsername(username);
    }
}




