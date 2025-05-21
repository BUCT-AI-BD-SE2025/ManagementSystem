package fun.yozora.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 操作日志表
 * @TableName operation_log
 */
@TableName(value ="operation_log")
@Data
public class OperationLog implements Serializable {
    /**
     * 日志ID
     */
    @TableId(type = IdType.AUTO)
    private Integer logId;

    /**
     * 操作人ID
     */
    private String operatorId;

    /**
     * 目标类型（如 artifact, comment）
     */
    private String targetType;

    /**
     * 目标ID
     */
    private String targetId;

    /**
     * 操作类型（如修改、删除）
     */
    private String actionType;

    /**
     * 修改前值
     */
    private Object oldValue;

    /**
     * 修改后值
     */
    private Object newValue;

    /**
     * IP地址
     */
    private String ipAddress;

    /**
     * 日志时间
     */
    private LocalDateTime logTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}