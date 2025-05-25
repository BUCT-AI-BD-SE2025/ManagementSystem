package fun.yozora.admin.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.yozora.admin.domain.entity.LoginLog;
import fun.yozora.admin.core.service.LoginLogService;
import fun.yozora.admin.repository.mapper.LoginLogMapper;
import org.springframework.stereotype.Service;

/**
* @author Yozor
* @description 针对表【login_log(用户登录日志)】的数据库操作Service实现
* @createDate 2025-05-22 04:03:17
*/
@Service
public class LoginLogServiceImpl extends ServiceImpl<LoginLogMapper, LoginLog>
    implements LoginLogService{

}




