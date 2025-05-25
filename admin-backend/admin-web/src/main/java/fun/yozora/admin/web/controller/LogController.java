package fun.yozora.admin.web.controller;

import cn.dev33.satoken.util.SaResult;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import fun.yozora.admin.core.service.LoginLogService;
import fun.yozora.admin.core.service.OperationLogService;
import fun.yozora.admin.domain.entity.LoginLog;
import fun.yozora.admin.domain.entity.OperationLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/log")
public class LogController {
    @Autowired
    private LoginLogService loginLogService;

    @Autowired
    private OperationLogService operationLogService;

    @GetMapping("login")
    public SaResult getLoginLogs(
            @RequestParam (defaultValue = "1") Integer page,
            @RequestParam (defaultValue = "20") Integer pageSize,
            @RequestParam (required = false) String logId,
            @RequestParam (required = false) String userId,
            @RequestParam (required = false) String username,
            @RequestParam (required = false) String ipAddress,
            @RequestParam (required = false) String deviceInfo,
            @RequestParam (required = false) Integer isSuccess,
            @RequestParam (required = false) String startTime,
            @RequestParam (required = false) String endTime,
            @RequestParam (required = false) String message
    ) {
        QueryWrapper<LoginLog> queryWrapper = new QueryWrapper<>();
        if(logId !=null&&!logId.isEmpty())
            queryWrapper.eq("log_id", logId);
        if(userId !=null&&!userId.isEmpty())
            queryWrapper.eq("user_id", userId);
        if(username !=null&&!username.isEmpty())
            queryWrapper.like("username", username);
        if(ipAddress !=null&&!ipAddress.isEmpty())
            queryWrapper.like("ip_address", ipAddress);
        if(deviceInfo !=null&&!deviceInfo.isEmpty())
            queryWrapper.like("device_info", deviceInfo);
        if(isSuccess !=null)
            queryWrapper.eq("is_success", isSuccess);
        if(startTime !=null)
            queryWrapper.ge("login_time", startTime);
        if(endTime !=null)
            queryWrapper.le("login_time", endTime);

        if(message !=null&&!message.isEmpty())
            queryWrapper.like("message", message);
        Page<LoginLog> loginLogPage = loginLogService.page(new Page<>(page, pageSize), queryWrapper);
        return SaResult.data(loginLogPage);

    }
    @GetMapping("operation")
    public SaResult getOperationLogs(
            @RequestParam (defaultValue = "1") Integer page,
            @RequestParam (defaultValue = "20") Integer pageSize,
            @RequestParam (required = false) String logId,
            @RequestParam (required = false) String operatorId,
            @RequestParam (required = false) String targetType,
            @RequestParam (required = false) String targetId,
            @RequestParam (required = false) String actionType,
            @RequestParam (required = false) Object oldValue,
            @RequestParam (required = false) Object newValue,
            @RequestParam (required = false) String ipAddress,
            @RequestParam (required = false) String startTime,
            @RequestParam (required = false) String endTime
    )
    {
        QueryWrapper<OperationLog> queryWrapper = new QueryWrapper<>();
        if (logId != null&&!logId.isEmpty())
            queryWrapper.eq("log_id", logId);
        if (operatorId != null&&!operatorId.isEmpty())
            queryWrapper.eq("operator_id", operatorId);
        if (targetType != null&&!targetType.isEmpty())
            queryWrapper.like("target_type", targetType);
        if (targetId != null&&!targetId.isEmpty())
            queryWrapper.eq("target_id", targetId);
        if (actionType != null&&!actionType.isEmpty())
            queryWrapper.like("action_type", actionType);
        if (ipAddress != null&&!ipAddress.isEmpty())
            queryWrapper.eq("ip_address", ipAddress);
        if(startTime !=null)
            queryWrapper.ge("log_time", startTime);
        if(endTime !=null)
            queryWrapper.le("log_time", endTime);
        Page<OperationLog> operationLogPage = operationLogService.page(new Page<>(page, pageSize), queryWrapper);
        return SaResult.data(operationLogPage);
    }
}
