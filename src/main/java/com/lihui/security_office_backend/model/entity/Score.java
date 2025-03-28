package com.lihui.security_office_backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 学生分数
 * @TableName score
 */
@TableName(value ="score")
@Data
public class Score implements Serializable {
    /**
     * 分数记录ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 学生ID
     */
    private Long studentId;

    /**
     * 内容ID
     */
    private Long contentId;

    /**
     * 获得的分数
     */
    private Integer score;

    /**
     * 学习时长（分钟）
     */
    private Integer learningTime;

    /**
     * 完成状态
     */
    private Object completionStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}