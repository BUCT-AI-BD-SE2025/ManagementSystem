package fun.yozora.admin.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.yozora.admin.core.entity.User;
import fun.yozora.admin.core.mapper.UserMapper;
import fun.yozora.admin.core.service.UserService;
import org.springframework.stereotype.Service;

/**
* @author YOZORA
* @description 针对表【user】的数据库操作Service实现
* @createDate 2025-05-12 00:32:16
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {

    @Override
    public User getUserByUsername(String username)
    {
        return baseMapper.selectByUsername(username);
    }

    @Override
    public boolean isExistUsername(String username)
    {
        return baseMapper.selectByUsername(username) != null;
    }

    @Override
    public boolean isExistEmail(String email) {
        return baseMapper.selectByEmail(email) != null;
    }

    @Override
    public boolean updateUser(User user) {
        return baseMapper.updateById(user) > 0;
    }

    @Override
    public boolean deleteUser(User user) {
        return baseMapper.deleteById(user) > 0;
    }

    @Override
    public boolean updateLastLogin(User user) {
        return baseMapper.updateLastLogin(user) > 0;
    }

    @Override
    public boolean updateIp(User user) {
        return baseMapper.updateIp(user) > 0;
    }

    @Override
    public boolean addUser(User user) {
        return baseMapper.insert(user) > 0;
    }

    @Override
    public User getUserByUid(String uid) {
        return baseMapper.selectById(uid);
    }
}




