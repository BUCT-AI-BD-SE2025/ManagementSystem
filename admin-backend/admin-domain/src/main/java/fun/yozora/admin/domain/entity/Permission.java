package fun.yozora.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;

/**
 * 
 * @TableName permission
 */
@TableName(value ="permission")
@Data
public class Permission implements Serializable {
    /**
     * 
     */
    @TableId
    private String permId;

    /**
     * 权限编码
     */
    @Getter
    private String permCode;

    /**
     * 
     */

    private String permName;

    /**
     * 
     */
    private String description;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}