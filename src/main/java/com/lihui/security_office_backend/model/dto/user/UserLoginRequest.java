package com.lihui.security_office_backend.model.dto.user;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;

/**
 * 用户信息
 * @TableName user
 */
@TableName(value ="user")
@Data
public class UserLoginRequest implements Serializable {

    /**
     * 密码
     */
    private String userPassword;

    /**
     * 身份证号
     */
    private String idNumber;

    /**
     * 学号/工号
     */
    private String studentNumber;


}