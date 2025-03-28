package com.lihui.security_office_backend.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 用户信息
 * @TableName user
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 用户ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 学号/工号
     */
    private String studentNumber;

    /**
     * 出生日期
     */
    private Date dateOfBirth;

    /**
     * 用户角色：admin/student
     */
    private Object role;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除(逻辑删除)
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}