package fun.yozora.admin.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName artifact
 */
@TableName(value ="artifact")
@Data
public class Artifact implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String originId;

    /**
     * 
     */
    private String title;

    /**
     * 
     */
    private String url;

    /**
     * 
     */
    private String image;

    /**
     * 
     */
    private String location;

    /**
     * 
     */
    private String period;

    /**
     * 
     */
    private String material;

    /**
     * 
     */
    private String measurement;

    /**
     * 
     */
    private String artist;

    /**
     * 
     */
    private String creditLine;

    /**
     * 
     */
    private String type;

    /**
     * 
     */
    private String description;

    /**
     * 
     */
    private String illusion;

    @Serial
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}