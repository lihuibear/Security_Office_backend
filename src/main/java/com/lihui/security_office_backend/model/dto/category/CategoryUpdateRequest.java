package com.lihui.security_office_backend.model.dto.category;

import lombok.Data;

import java.io.Serializable;

/**
 * 内容分类
 * @TableName category
 */
@Data
public class CategoryUpdateRequest implements Serializable {

    private Long id;
    /**
     * 分类名称
     */
    private String categoryName;

    /**
     * 分类描述
     */
    private String description;

    /**
     * 分类封面图URL
     */
    private String coverUrl;


    private static final long serialVersionUID = 1L;
}