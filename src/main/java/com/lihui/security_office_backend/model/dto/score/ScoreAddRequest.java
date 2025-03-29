package com.lihui.security_office_backend.model.dto.score;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 学生分数
 * @TableName score
 */
@Data
public class ScoreAddRequest implements Serializable {


    /**
     * 内容ID
     */
    private Long contentId;


    /**
     * 学习时长（分钟）
     */
    private Integer learningTime;



    private static final long serialVersionUID = 1L;


}