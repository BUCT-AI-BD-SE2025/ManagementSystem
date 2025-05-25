package fun.yozora.admin.core.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import fun.yozora.admin.domain.entity.OperationLog;
import fun.yozora.admin.core.service.OperationLogService;
import fun.yozora.admin.repository.mapper.OperationLogMapper;
import org.springframework.stereotype.Service;

/**
* @author Yozor
* @description 针对表【operation_log(操作日志表)】的数据库操作Service实现
* @createDate 2025-05-22 03:53:17
*/
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog>
    implements OperationLogService{

}




