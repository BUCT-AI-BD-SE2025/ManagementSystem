package fun.yozora.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName text_moderation_result
 */
@TableName(value ="text_moderation_result")
@Data
public class TextModerationResult implements Serializable {
    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 请求中的BizType参数
     */
    private String bizType;

    /**
     * 优先级最高的恶意标签: Normal/Porn/Abuse/Ad等
     */
    private String label;

    /**
     * 建议操作: Block/Review/Pass
     */
    private String suggestion;

    /**
     * 命中的关键词列表，JSON数组格式
     */
    private String keywords;

    /**
     * 置信度评分（0-100）
     */
    private long score;

    /**
     * 详细检测结果（JSON对象）
     */
    private Object detailResults;

    /**
     * 账号风险详情（JSON对象）
     */
    private Object riskDetails;

    /**
     * 附加信息
     */
    private String extraInfo;

    /**
     * 请求时传入的数据ID
     */
    private String dataId;

    /**
     * 二级标签
     */
    private String subLabel;

    /**
     * 上下文关联文本
     */
    private String contextText;

    /**
     * 情感分析结果（JSON对象）
     */
    private Object sentimentAnalysis;

    /**
     * 匹配类型：text_nlp_tianji/text_keyword_public等
     */
    private String hitType;

    /**
     * 唯一请求ID
     */
    private String requestId;

    /**
     * 原始审核文本内容
     */
    private String content;

    /**
     * 记录创建时间
     */
    private LocalDateTime createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}