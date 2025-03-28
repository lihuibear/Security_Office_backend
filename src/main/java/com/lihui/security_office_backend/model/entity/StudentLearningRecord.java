package com.lihui.security_office_backend.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 学生学习记录
 * @TableName student_learning_record
 */
@TableName(value ="student_learning_record")
@Data
public class StudentLearningRecord implements Serializable {
    /**
     * 学习记录ID
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
     * 学习进度（0-100%）
     */
    private Double progress;

    /**
     * 最后访问时间
     */
    private Date lastAccessTime;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}