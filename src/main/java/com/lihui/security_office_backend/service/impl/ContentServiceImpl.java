package com.lihui.security_office_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lihui.security_office_backend.mapper.ContentMapper;
import com.lihui.security_office_backend.model.dto.content.ContentAddRequest;
import com.lihui.security_office_backend.model.entity.Content;
import com.lihui.security_office_backend.model.entity.User;
import com.lihui.security_office_backend.service.CategoryService;
import com.lihui.security_office_backend.service.ContentService;
import com.lihui.security_office_backend.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements ContentService {

    @Resource
    private UserService userService;
    @Resource
    private CategoryService categoryService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean addContent(ContentAddRequest contentAddRequest, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            throw new IllegalArgumentException("用户未登录");
        }

        Long categoryId = contentAddRequest.getCategoryId();
        if (!categoryService.existsById(categoryId)) {
            throw new IllegalArgumentException("分类不存在");
        }

        Content content = convertToEntity(contentAddRequest, loginUser);
        return save(content);
    }

    private Content convertToEntity(ContentAddRequest contentAddRequest, User loginUser) {
        Content content = new Content();
        content.setTitle(contentAddRequest.getTitle());
        content.setDescription(contentAddRequest.getDescription());
        content.setType(contentAddRequest.getType());
        content.setUrl(contentAddRequest.getUrl());
        content.setScore(contentAddRequest.getScore());
        content.setCategoryId(contentAddRequest.getCategoryId());
        content.setCoverUrl(contentAddRequest.getCoverUrl());
        content.setUserId(loginUser.getId());
        return content;
    }
}