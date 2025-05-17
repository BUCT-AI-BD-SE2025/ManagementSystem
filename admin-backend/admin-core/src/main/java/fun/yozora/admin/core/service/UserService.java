package fun.yozora.admin.core.service;

import com.baomidou.mybatisplus.extension.service.IService;
import fun.yozora.admin.core.entity.User;

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
