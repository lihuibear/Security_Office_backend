package com.lihui.security_office_backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lihui.security_office_backend.common.BaseResponse;
import com.lihui.security_office_backend.model.dto.content.ContentAddRequest;
import com.lihui.security_office_backend.model.dto.content.ContentQueryRequest;
import com.lihui.security_office_backend.model.dto.content.ContentUpdataRequest;
import com.lihui.security_office_backend.model.entity.Content;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lihui.security_office_backend.model.vo.ContentVO;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author lihui
 * @description 针对表【content(学习内容)】的数据库操作Service
 * @createDate 2025-03-28 09:33:49
 */
public interface ContentService extends IService<Content> {
    @Transactional(rollbackFor = Exception.class)
    boolean addContent(ContentAddRequest contentAddRequest, HttpServletRequest request);

    boolean deleteContent(Long id);

//    boolean updateContent(ContentUpdataRequest contentUpdataRequest, HttpServletRequest request);


    @Transactional(rollbackFor = Exception.class)
    boolean updateContent(ContentUpdataRequest contentUpdataRequest, HttpServletRequest request);

    List<ContentVO> getContentList();

    QueryWrapper<Content> getQueryWrapper(ContentQueryRequest contentQueryRequest);

    BaseResponse<ContentVO> getContentById(Long id);


//    boolean addContent(ContentAddRequest contentAddRequest, HttpServletRequest request);

//    boolean addContent(ContentAddRequest contentAddRequest, HttpServletRequest request,
//                       MultipartFile videoFile, MultipartFile coverFile);
}
