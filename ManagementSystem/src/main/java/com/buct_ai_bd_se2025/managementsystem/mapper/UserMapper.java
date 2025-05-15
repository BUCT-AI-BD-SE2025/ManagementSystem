package com.buct_ai_bd_se2025.managementsystem.mapper;

import com.buct_ai_bd_se2025.managementsystem.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;

/**
* @author YOZORA
* @description 针对表【user】的数据库操作Mapper
* @createDate 2025-05-12 00:32:16
* @Entity com.buct_ai_bd_se2025.managementsystem.entity.User
*/

public interface UserMapper extends BaseMapper<User>
{
    User selectByUsername(String username);

    User selectByEmail(String email);

    //update ip
    int updateIp(User user);

    //update last login
    int updateLastLogin(User user);
}




