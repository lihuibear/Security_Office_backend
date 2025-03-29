package com.lihui.security_office_backend.model.entity;

import com.alibaba.excel.annotation.ExcelProperty;
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
    @ExcelProperty(value = "用户ID", index = 0)
    private Long id;

    /**
     * 用户名
     */
    @ExcelProperty(value = "用户名", index = 1)
    private String userName;

    /**
     * 密码
     */
    @ExcelProperty(value = "密码", index = 2)
    private String userPassword;

    /**
     * 用户头像
     */
    @ExcelProperty(value = "用户头像", index = 3)
    private String userAvatar;

    /**
     * 身份证号
     */
    @ExcelProperty(value = "身份证号", index = 4)
    private String idNumber;

    /**
     * 学号/工号
     */
    @ExcelProperty(value = "学号", index = 5)
    private String studentNumber;

    /**
     * 出生日期
     */
    @ExcelProperty(value = "出生日期", index = 6)
    private Date dateOfBirth;

    /**
     * 用户角色：admin/student
     */
    @ExcelProperty(value = "用户角色", index = 7)
    private String role;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @ExcelProperty(value = "创建时间", index = 8)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @ExcelProperty(value = "更新时间", index = 9)
    private Date updateTime;

    /**
     * 是否删除(逻辑删除)
     */
    @TableLogic
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}