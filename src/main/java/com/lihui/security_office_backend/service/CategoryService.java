package com.lihui.security_office_backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lihui.security_office_backend.model.dto.category.CategoryDeleteRequest;
import com.lihui.security_office_backend.model.dto.category.CategoryQueryRequest;
import com.lihui.security_office_backend.model.dto.category.CategoryUpdateRequest;
import com.lihui.security_office_backend.model.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lihui.security_office_backend.model.vo.CategoryVO;

import java.util.List;

/**
 * @author lihui
 * @description 针对表【category(内容分类)】的数据库操作Service
 * @createDate 2025-03-28 09:33:49
 */
public interface CategoryService extends IService<Category> {

    /**
     * 根据分类名获取分类
     */
    Category getCategoryByName(String categoryName);

    /**
     * 获取分类列表
     */
    List<CategoryVO> getCategoryList();

    /**
     * 分页获取分类列表
     */
    Page<CategoryVO> getCategoryList(Page<Category> page);

    /**
     * 根据条件获取查询条件
     *
     * @param categoryQueryRequest
     * @return
     */
    QueryWrapper<Category> getQueryWrapper(CategoryQueryRequest categoryQueryRequest);

    /**
     * 删除分类
     */
    boolean deleteCategory(CategoryDeleteRequest categoryDeleteRequest);

    /**
     * 修改分类
     */
    boolean updateCategory(CategoryUpdateRequest categoryUpdateRequest);

    boolean existsById(Long categoryId);
}
