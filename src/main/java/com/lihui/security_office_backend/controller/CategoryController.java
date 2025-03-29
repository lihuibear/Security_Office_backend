package com.lihui.security_office_backend.controller;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lihui.security_office_backend.annotation.AuthCheck;
import com.lihui.security_office_backend.common.BaseResponse;
import com.lihui.security_office_backend.common.ResultUtils;
import com.lihui.security_office_backend.constant.UserConstant;
import com.lihui.security_office_backend.exception.ErrorCode;
import com.lihui.security_office_backend.exception.ThrowUtils;
import com.lihui.security_office_backend.model.dto.category.CategoryAddRequest;
import com.lihui.security_office_backend.model.dto.category.CategoryDeleteRequest;
import com.lihui.security_office_backend.model.dto.category.CategoryQueryRequest;
import com.lihui.security_office_backend.model.dto.category.CategoryUpdateRequest;
import com.lihui.security_office_backend.model.entity.Category;
import com.lihui.security_office_backend.model.vo.CategoryVO;
import com.lihui.security_office_backend.service.CategoryService;

import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Resource
    private CategoryService categoryService;

    /**
     * 创建分类（如果分类已存在，则返回已有分类ID）
     */
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)

    @PostMapping("/add")
    public BaseResponse<Long> addCategory(@RequestBody CategoryAddRequest categoryAddRequest) {
        // 校验分类名称是否为空
        String categoryName = categoryAddRequest.getCategoryName();
        ThrowUtils.throwIf(StringUtils.isBlank(categoryName), ErrorCode.PARAMS_ERROR, "分类名称不能为空");

        // 检查分类是否已存在
        Category existingCategory = categoryService.getCategoryByName(categoryName);
        if (existingCategory != null) {
            // 如果分类已存在，直接返回已有分类的ID
            return ResultUtils.success(existingCategory.getId());
        }

        // 如果分类不存在，则创建新分类
        Category category = new Category();
        BeanUtils.copyProperties(categoryAddRequest, category);

        // 保存分类
        boolean result = categoryService.save(category);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR, "创建分类失败");

        // 返回新分类的ID
        return ResultUtils.success(category.getId());
    }

    /**
     * 根据分类名称查询分类信息
     */
    @GetMapping("/getByName")
    public BaseResponse<Category> getCategoryByName(@RequestParam String categoryName) {
        ThrowUtils.throwIf(StringUtils.isBlank(categoryName), ErrorCode.PARAMS_ERROR, "分类名称不能为空");

        // 调用 Service 层方法查询分类
        Category category = categoryService.getCategoryByName(categoryName);
        ThrowUtils.throwIf(category == null, ErrorCode.NOT_FOUND_ERROR, "分类不存在");

        return ResultUtils.success(category);
    }

    /**
     * 获取分类列表
     */
    @GetMapping("/list")
    public BaseResponse<List<CategoryVO>> getCategoryList() {
        List<CategoryVO> categoryList = categoryService.getCategoryList();
        return ResultUtils.success(categoryList);
    }

    /**
     * 分页获取分类列表
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<Category>> getCategoryListByPage(
            @RequestBody CategoryQueryRequest categoryQueryRequest) {
        long current = categoryQueryRequest.getCurrent();
        long size = categoryQueryRequest.getPageSize();
        // 查数据库
        Page<Category> categoryPage = categoryService.page(new Page<>(current, size),
                categoryService.getQueryWrapper(categoryQueryRequest));
        return ResultUtils.success(categoryPage);

    }

    /**
     * 删除分类
     */
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteCategory(@RequestBody CategoryDeleteRequest categoryDeleteRequest) {
        boolean b = categoryService.deleteCategory(categoryDeleteRequest);
        return ResultUtils.success(b);
    }

    /**
     * 更新分类信息
     */
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)

    @PostMapping("/update")
    public BaseResponse<Boolean> updateCategory(@RequestBody CategoryUpdateRequest categoryUpdateRequest) {
        boolean b = categoryService.updateCategory(categoryUpdateRequest);
        return ResultUtils.success(b);
    }


}