package com.lihui.security_office_backend.model.vo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 学习内容
 *
 * @TableName content
 */

@Data
public class ContentVO implements Serializable {
    /**
     * 内容ID
     */
    private Long id;

    /**
     * 内容标题
     */
    private String title;

    /**
     * 内容描述
     */
    private String description;

    /**
     * 内容类型：video/article
     */
    private Object type;

    /**
     * 内容URL
     */
    private String url;

    /**
     * 内容对应的分数
     */
    private Integer score;

    /**
     * 分类ID
     */
    private Long categoryId;

    /**
     * 内容封面图URL
     */
    private String coverUrl;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 学习时长（分钟）
     */
    private Integer learningTime;



    /**
     * 上传内容的用户ID
     */
    private Long userId;

    private static final long serialVersionUID = 1L;
}