package com.lihui.security_office_backend.model.dto.category;

import lombok.Data;

import java.io.Serializable;

/**
 * 内容分类
 * @TableName category
 */
@Data
public class CategoryDeleteRequest implements Serializable {


    /**
     * 分类名称
     */
    private String categoryName;


    private static final long serialVersionUID = 1L;
}