package fun.yozora.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 人工审核表
 * @TableName comment_review_result
 */
@TableName(value ="comment_review_result")
@Data
public class CommentReviewResult implements Serializable {
    /**
     * 审核ID
     */
    @TableId
    private String reviewId;

    /**
     * 审核内容ID
     */
    private String commentId;

    /**
     * 操作人ID
     */
    private String reviewerId;

    /**
     * 审核状态
     */
    private String status;

    /**
     * 审核结果（0待审, 1通过, 2驳回）
     */
    private String reviewResult;

    /**
     * 驳回理由
     */
    private String blockReason;

    /**
     * 审核时间
     */
    private LocalDateTime reviewTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}