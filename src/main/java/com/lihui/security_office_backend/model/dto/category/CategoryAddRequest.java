package com.lihui.security_office_backend.model.dto.category;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 内容分类
 * @TableName category
 */
@Data
public class CategoryAddRequest implements Serializable {


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