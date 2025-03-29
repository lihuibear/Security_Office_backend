package com.lihui.security_office_backend.controller;

import com.lihui.security_office_backend.annotation.AuthCheck;
import com.lihui.security_office_backend.common.BaseResponse;
import com.lihui.security_office_backend.common.ResultUtils;
import com.lihui.security_office_backend.constant.UserConstant;
import com.lihui.security_office_backend.exception.BusinessException;
import com.lihui.security_office_backend.exception.ErrorCode;
import com.lihui.security_office_backend.exception.ThrowUtils;
import com.lihui.security_office_backend.model.dto.user.UserEditRequest;
import com.lihui.security_office_backend.model.dto.user.UserLoginRequest;
import com.lihui.security_office_backend.model.entity.User;
import com.lihui.security_office_backend.model.vo.LoginUserVO;
import com.lihui.security_office_backend.service.UserService;
import com.lihui.security_office_backend.utils.ExcelUtils;
import com.lihui.security_office_backend.utils.UserExcelListener;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户登录
     *
     * @param userLoginRequest
     * @param request
     * @return
     */
    @PostMapping("/login")
    public BaseResponse<LoginUserVO> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        ThrowUtils.throwIf(userLoginRequest == null, ErrorCode.PARAMS_ERROR);
        String idNumber = userLoginRequest.getIdNumber();
        String userPassword = userLoginRequest.getUserPassword();
        LoginUserVO loginUserVO = userService.userLogin(idNumber, userPassword, request);
        return ResultUtils.success(loginUserVO);
    }

    /**
     * 获取当前登录用户信息
     *
     * @param request
     * @return
     */
    @GetMapping("/get/login")
    public BaseResponse<LoginUserVO> getLoginUser(HttpServletRequest request) {
        User user = userService.getLoginUser(request);
        return ResultUtils.success(userService.getLoginUserVO(user));
    }

    /**
     * 用户登出
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public BaseResponse<Boolean> userLogout(HttpServletRequest request) {
        ThrowUtils.throwIf(request == null, ErrorCode.PARAMS_ERROR, "请求为空");
        boolean result = userService.userLogout(request);
        return ResultUtils.success(result);
    }

    //编辑用户信息
    //todo 编辑用户信息
    @AuthCheck(mustRoles = {UserConstant.ADMIN_ROLE, UserConstant.DEFAULT_ROLE})

    @PostMapping("/edit")
    public BaseResponse<Boolean> editUser(@RequestBody UserEditRequest userEditRequest, HttpServletRequest request) {
        // 获取当前登录用户的详细信息
        User loginUser = userService.getLoginUser(request);  // 传递 request 参数
        // 校验参数
        if (userEditRequest == null || userEditRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户ID不能为空");
        }
        // 如果编辑的是其他用户的资料，需要判断权限
        if (!userEditRequest.getId().equals(loginUser.getId())) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR, "没有权限编辑其他用户信息");
        }
        // 创建用户对象并复制编辑请求中的数据
        User userToUpdate = new User();
        BeanUtils.copyProperties(userEditRequest, userToUpdate);
        // 更新用户信息
        boolean result = userService.updateById(userToUpdate);
        if (!result) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "更新失败");
        }
        // 返回更新成功的结果
        return ResultUtils.success(true);

    }

    /**
     * 导出用户列表
     *
     * @return
     */
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @GetMapping("/export")
    public BaseResponse<String> exportUsers(HttpServletResponse response) {
        List<User> userList = userService.getAllUsers();
        String fileName = "用户列表";
        Set<String> excludeColumnFieldNames = null;
        ExcelUtils.exportExcel(response, userList, fileName, User.class, excludeColumnFieldNames);

        return ResultUtils.success("用户列表导出成功");
    }

    /**
     * 导入用户数据
     */
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    @PostMapping("/import")
    public BaseResponse<String> importUsers(@RequestParam("file") MultipartFile file) {
        try {
            ExcelUtils.importExcel(file, User.class, new UserExcelListener(userService));
            return ResultUtils.success("用户导入成功");

        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "用户导入失败：" + e.getMessage());
        }
    }


}