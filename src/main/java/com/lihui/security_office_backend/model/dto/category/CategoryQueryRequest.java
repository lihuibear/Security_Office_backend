package com.lihui.security_office_backend.model.dto.category;

import com.lihui.security_office_backend.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 内容分类
 * @TableName category
 */
@Data
public class CategoryQueryRequest extends PageRequest implements Serializable {


    /**
     * 分类ID
     */
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

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;



    private static final long serialVersionUID = 1L;
}