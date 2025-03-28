package com.lihui.security_office_backend.model.dto.content;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 学习内容
 *
 * @TableName content
 */
@Data
public class ContentDeleteRequest implements Serializable {
    /**
     * 内容ID
     */
    private Long id;

    private static final long serialVersionUID = 1L;
}