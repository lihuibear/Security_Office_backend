package com.lihui.security_office_backend.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lihui.security_office_backend.exception.BusinessException;
import com.lihui.security_office_backend.exception.ErrorCode;
import com.lihui.security_office_backend.mapper.ContentMapper;
import com.lihui.security_office_backend.model.dto.category.CategoryQueryRequest;
import com.lihui.security_office_backend.model.dto.content.ContentAddRequest;
import com.lihui.security_office_backend.model.dto.content.ContentQueryRequest;
import com.lihui.security_office_backend.model.dto.content.ContentUpdataRequest;
import com.lihui.security_office_backend.model.entity.Category;
import com.lihui.security_office_backend.model.entity.Content;
import com.lihui.security_office_backend.model.entity.User;
import com.lihui.security_office_backend.model.vo.ContentVO;
import com.lihui.security_office_backend.service.CategoryService;
import com.lihui.security_office_backend.service.ContentService;
import com.lihui.security_office_backend.service.UserService;
import com.lihui.security_office_backend.utils.FileUploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content> implements ContentService {

    @Resource
    private UserService userService;
    @Resource
    private CategoryService categoryService;
    @Autowired
    private ContentMapper contentMapper;


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

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteContent(Long id) {
        Content content = getById(id);
        if (content == null) {
            return false;
        }

        FileUploadUtils.deleteFile(content.getUrl());
        FileUploadUtils.deleteFile(content.getCoverUrl());

        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateContent(ContentUpdataRequest contentUpdataRequest, HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        //todo 管理员
        if (loginUser == null) {
            throw new BusinessException(ErrorCode.NOT_LOGIN_ERROR, "用户未登录");
        }
// 不用检查分类，能改肯定有分类
//        Long categoryId = contentUpdataRequest.getCategoryId();
//        if (!categoryService.existsById(categoryId)) {
//            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "分类不存在");
//        }

        Content content = getById(contentUpdataRequest.getId());
        if (content == null) {
            return false;
        }
        Date currentTime = new Date();
        // 更新非文件相关的属性
        content.setTitle(contentUpdataRequest.getTitle());
        content.setDescription(contentUpdataRequest.getDescription());
        content.setScore(contentUpdataRequest.getScore());
        content.setCategoryId(contentUpdataRequest.getCategoryId());
        content.setUpdateTime(currentTime);

        // 更新文件相关的属性
        if (contentUpdataRequest.getUrl() != null) {
            FileUploadUtils.deleteFile(content.getUrl());
            content.setUrl(contentUpdataRequest.getUrl());
            content.setType(contentUpdataRequest.getType());
        }
        if (contentUpdataRequest.getCoverUrl() != null) {
            FileUploadUtils.deleteFile(content.getCoverUrl());
            content.setCoverUrl(contentUpdataRequest.getCoverUrl());
        }

        return updateById(content);
    }

    @Override
    public List<ContentVO> getContentList() {
        // 查询所有内容
        List<Content> categoryList = contentMapper.selectList(null);
        // 转换为 ContentVO 列表
        return categoryList.stream()
                .map(content -> {
                    ContentVO contentVO = new ContentVO();
                    contentVO.setId(content.getId());
                    contentVO.setTitle(content.getTitle());
                    contentVO.setDescription(content.getDescription());
                    contentVO.setScore(content.getScore());
                    contentVO.setCategoryId(content.getCategoryId());
                    contentVO.setCoverUrl(content.getCoverUrl());
                    contentVO.setUrl(content.getUrl());
                    contentVO.setCreateTime(content.getCreateTime());
                    contentVO.setUpdateTime(content.getUpdateTime());
                    return contentVO;

                }).collect(Collectors.toList());

    }

    @Override
    public QueryWrapper<Content> getQueryWrapper(ContentQueryRequest contentQueryRequest) {

        // 创建 QueryWrapper 对象
        QueryWrapper<Content> queryWrapper = new QueryWrapper<>();
        if (contentQueryRequest == null) {
            return queryWrapper;
        }

        Long id = contentQueryRequest.getId();
        String title = contentQueryRequest.getTitle();
        String description = contentQueryRequest.getDescription();
        Object type = contentQueryRequest.getType();
        String url = contentQueryRequest.getUrl();
        Integer score = contentQueryRequest.getScore();
        Long categoryId = contentQueryRequest.getCategoryId();
        String coverUrl = contentQueryRequest.getCoverUrl();
        Date createTime = contentQueryRequest.getCreateTime();
        Date updateTime = contentQueryRequest.getUpdateTime();
        String sortField = contentQueryRequest.getSortField();
        String sortOrder = contentQueryRequest.getSortOrder();

        // 拼接查询条件
        queryWrapper.eq(ObjUtil.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjUtil.isNotEmpty(title), "title", title);
        queryWrapper.eq(ObjUtil.isNotEmpty(description), "description", description);
        queryWrapper.eq(ObjUtil.isNotEmpty(type), "type", type);
        queryWrapper.eq(ObjUtil.isNotEmpty(url), "url", url);
        queryWrapper.eq(ObjUtil.isNotEmpty(score), "score", score);
        queryWrapper.eq(ObjUtil.isNotEmpty(categoryId), "categoryId", categoryId);
        queryWrapper.eq(ObjUtil.isNotEmpty(coverUrl), "coverUrl", coverUrl);
        queryWrapper.eq(ObjUtil.isNotEmpty(createTime), "createTime", createTime);
        queryWrapper.eq(ObjUtil.isNotEmpty(updateTime), "updateTime", updateTime);
        // 排序
        queryWrapper.orderBy(StrUtil.isNotEmpty(sortField), sortOrder.equals("ascend"), sortField);
        return queryWrapper;


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