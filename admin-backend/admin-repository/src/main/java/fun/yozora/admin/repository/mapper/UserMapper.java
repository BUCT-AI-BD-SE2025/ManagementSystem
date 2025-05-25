package fun.yozora.admin.repository.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import fun.yozora.admin.domain.entity.User;

/**
* @author YOZORA
* @description 针对表【user】的数据库操作Mapper
* @createDate 2025-05-12 00:32:16
* @Entity fun.yozora.admin.domain.entity.User
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




