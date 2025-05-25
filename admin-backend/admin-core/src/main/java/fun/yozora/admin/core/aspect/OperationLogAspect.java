package fun.yozora.admin.core.aspect;

import fun.yozora.admin.core.annotation.LogOperation;
import fun.yozora.admin.core.service.OperationLogService;
import fun.yozora.admin.domain.entity.OperationLog;
import cn.dev33.satoken.stp.StpUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.LocalDateTime;

@Aspect
@Component
public class OperationLogAspect {

    private final OperationLogService operationLogService;

    public OperationLogAspect(OperationLogService operationLogService) {
        this.operationLogService = operationLogService;
    }

    @Around("@annotation(logOperation)")
    public Object doOperationLog(ProceedingJoinPoint joinPoint, LogOperation logOperation) throws Throwable {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes != null ? attributes.getRequest() : null;

        OperationLog log = new OperationLog();
        log.setOperatorId(StpUtil.getLoginIdAsString());
        log.setTargetType(logOperation.targetType());
        log.setActionType(logOperation.actionType());
        log.setIpAddress(request != null ? request.getRemoteAddr() : "unknown");
        log.setLogTime(LocalDateTime.now());

        // 尝试从方法参数中提取 targetId（比如删除/更新操作）
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof String) {
                log.setTargetId((String) arg); // 假设第一个 String 参数是 targetId
                break;
            }
        }

        // 如果还是没找到，就设置一个默认值
        if (log.getTargetId() == null) {
            log.setTargetId("unknown");
        }

        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            log.setOldValue(e.getMessage());
            throw e;
        } finally {
            operationLogService.save(log);
        }
    }
}

