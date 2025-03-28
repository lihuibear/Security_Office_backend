package com.lihui.security_office_backend.model.vo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 *
 * @TableName user
 */
@TableName(value = "user")
@Data
public class UserVO implements Serializable {
    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    private String userName;


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

    private static final long serialVersionUID = 1L;
}