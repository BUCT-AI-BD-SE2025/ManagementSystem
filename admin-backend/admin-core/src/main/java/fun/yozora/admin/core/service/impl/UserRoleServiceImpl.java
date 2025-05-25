package fun.yozora.admin.core.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.yozora.admin.core.service.UserRoleService;
import fun.yozora.admin.domain.entity.UserRole;
import fun.yozora.admin.repository.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
implements UserRoleService  {

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public boolean assignRoles(String userId, List<String> roleIds) {
        QueryWrapper<UserRole> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        userRoleMapper.delete(queryWrapper);

        List<UserRole> list = new ArrayList<>();
        for (String roleId : roleIds)
        {
            UserRole ur = new UserRole();
            ur.setUserId(userId);
            ur.setRoleId(roleId);
            list.add(ur);
        }
        userRoleMapper.insert(list);
        return true;
    }
}
