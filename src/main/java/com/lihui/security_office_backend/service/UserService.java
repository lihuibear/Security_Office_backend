package com.lihui.security_office_backend.service;

import com.alibaba.excel.EasyExcel;
import com.lihui.security_office_backend.model.dto.user.UserEditRequest;
import com.lihui.security_office_backend.model.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lihui.security_office_backend.model.vo.LoginUserVO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

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


    List<User> getAllUsers();
}
