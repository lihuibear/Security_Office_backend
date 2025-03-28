package com.lihui.security_office_backend.service;

import com.lihui.security_office_backend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lihui.security_office_backend.model.vo.LoginUserVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author lihui
* @description 针对表【user(用户信息)】的数据库操作Service
* @createDate 2025-03-28 09:33:49
*/
public interface UserService extends IService<User> {

    /**
     * 用户密码加密
     *
     * @param userPassword
     * @return
     */
    String getEncryptPassword(String userPassword);

    LoginUserVO userLogin(String idNumber, String userPassword, HttpServletRequest request);


    LoginUserVO getLoginUserVO(User user);
    User getLoginUser(HttpServletRequest request);

    boolean userLogout(HttpServletRequest request);
}
