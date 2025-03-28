package com.lihui.security_office_backend.service;

import com.lihui.security_office_backend.model.dto.content.ContentAddRequest;
import com.lihui.security_office_backend.model.entity.Content;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
* @author lihui
* @description 针对表【content(学习内容)】的数据库操作Service
* @createDate 2025-03-28 09:33:49
*/
public interface ContentService extends IService<Content> {
    @Transactional(rollbackFor = Exception.class)
    boolean addContent(ContentAddRequest contentAddRequest, HttpServletRequest request);

//    boolean addContent(ContentAddRequest contentAddRequest, HttpServletRequest request);

//    boolean addContent(ContentAddRequest contentAddRequest, HttpServletRequest request,
//                       MultipartFile videoFile, MultipartFile coverFile);
}
