package com.lihui.security_office_backend.model.dto.user;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息
 * @TableName user
 */
@TableName(value ="user")
@Data
public class UserEditRequest implements Serializable {
    /**
     * 用户ID
     */
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
     * 出生日期
     */
    private Date dateOfBirth;


    /**
     * 更新时间
     */
    private Date updateTime;



    private static final long serialVersionUID = 1L;


}