package com.lihui.security_office_backend.service.impl;

import java.util.Date;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lihui.security_office_backend.exception.BusinessException;
import com.lihui.security_office_backend.exception.ErrorCode;
import com.lihui.security_office_backend.model.dto.category.CategoryDeleteRequest;
import com.lihui.security_office_backend.model.dto.category.CategoryQuerryRequest;
import com.lihui.security_office_backend.model.dto.category.CategoryUpdateRequest;
import com.lihui.security_office_backend.model.entity.Category;
import com.lihui.security_office_backend.model.vo.CategoryVO;
import com.lihui.security_office_backend.service.CategoryService;
import com.lihui.security_office_backend.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lihui
 * @description 针对表【category(内容分类)】的数据库操作Service实现
 * @createDate 2025-03-28 09:33:49
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
        implements CategoryService {

    @Resource
    private CategoryMapper categoryMapper;

    @Override
    public Category getCategoryByName(String categoryName) {
        // 1. 校验
        if (StrUtil.hasBlank(categoryName)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        // 2. 查询
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("categoryName", categoryName);
        return categoryMapper.selectOne(queryWrapper);
    }

    @Override
    public List<CategoryVO> getCategoryList() {
        // 查询所有分类
        List<Category> categoryList = categoryMapper.selectList(null);

        // 转换为 CategoryVO 列表
        return categoryList.stream()
                .map(category -> {
                    CategoryVO categoryVO = new CategoryVO();
                    categoryVO.setId(category.getId());
                    categoryVO.setCategoryName(category.getCategoryName());
                    categoryVO.setDescription(category.getDescription());
                    return categoryVO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Page<CategoryVO> getCategoryList(Page<Category> page) {
        // 查询分页数据
        Page<Category> categoryPage = categoryMapper.selectPage(page, null);

        // 将 Category 转换为 CategoryVO
        List<CategoryVO> categoryVOList = categoryPage.getRecords().stream()
                .map(category -> {
                    CategoryVO categoryVO = new CategoryVO();
                    categoryVO.setId(category.getId());
                    categoryVO.setCategoryName(category.getCategoryName());
                    categoryVO.setDescription(category.getDescription());
                    return categoryVO;
                })
                .collect(Collectors.toList());

        // 将转换后的数据设置到分页对象中
        Page<CategoryVO> categoryVOPage = new Page<>();
        categoryVOPage.setCurrent(categoryPage.getCurrent()); // 当前页码
        categoryVOPage.setSize(categoryPage.getSize());         // 每页大小
        categoryVOPage.setTotal(categoryPage.getTotal());       // 总记录数
        categoryVOPage.setPages(categoryPage.getPages());       // 总页数
        categoryVOPage.setRecords(categoryVOList);             // 数据列表

        return categoryVOPage;
    }

    @Override
    public QueryWrapper<Category> getQueryWrapper(CategoryQuerryRequest categoryQuerryRequest) {

        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        if (categoryQuerryRequest == null) {
            return queryWrapper;
        }
        // 从对象中取值
        Long id = categoryQuerryRequest.getId();
        String categoryName = categoryQuerryRequest.getCategoryName();
        String description = categoryQuerryRequest.getDescription();

        String sortField = categoryQuerryRequest.getSortField();
        String sortOrder = categoryQuerryRequest.getSortOrder();
        // 拼接查询条件
        queryWrapper.eq(ObjUtil.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjUtil.isNotEmpty(categoryName), "categoryName", categoryName);
        queryWrapper.like(StrUtil.isNotBlank(description), "description", description);

        // 排序
        queryWrapper.orderBy(StrUtil.isNotEmpty(sortField), sortOrder.equals("ascend"), sortField);
        return queryWrapper;
    }

    @Override
    public boolean deleteCategory(CategoryDeleteRequest categoryDeleteRequest) {
        String categoryName = categoryDeleteRequest.getCategoryName();
        // 1. 校验
        if (StrUtil.hasBlank(categoryName)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }

        // 2. 检查分类是否存在
        Category existingCategory = categoryMapper.selectOne(new QueryWrapper<Category>().eq("categoryName", categoryName));
        if (existingCategory == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "分类不存在");
        }

        // 3. 删除
        return categoryMapper.delete(new QueryWrapper<Category>().eq("categoryName", categoryName)) > 0;
    }

    @Override
    public boolean updateCategory(CategoryUpdateRequest categoryUpdateRequest) {
        // 1. 校验参数
        if (categoryUpdateRequest == null || categoryUpdateRequest.getId() == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空或分类ID不能为空");
        }

        // 2. 查询分类是否存在
        Long categoryId = categoryUpdateRequest.getId(); // 获取传入的分类ID
        Category existingCategory = categoryMapper.selectById(categoryId);
        if (existingCategory == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "分类不存在");
        }

        // 3. 更新分类信息（禁止更新ID）
        if (StrUtil.isNotBlank(categoryUpdateRequest.getCategoryName())) {
            existingCategory.setCategoryName(categoryUpdateRequest.getCategoryName());
        }
        if (StrUtil.isNotBlank(categoryUpdateRequest.getDescription())) {
            existingCategory.setDescription(categoryUpdateRequest.getDescription());
        }
        if (StrUtil.isNotBlank(categoryUpdateRequest.getCoverUrl())) {
            existingCategory.setCoverUrl(categoryUpdateRequest.getCoverUrl());
        }

        // 4. 确保ID不会被修改
        existingCategory.setId(categoryId); // 显式地将ID设置为原始值

        // 5. 保存更新
        return categoryMapper.updateById(existingCategory) > 0;
    }


    @Override
    public boolean existsById(Long categoryId) {
        // 使用 MyBatis-Plus 的查询构造器检查是否存在
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Category::getId, categoryId);
        return count(queryWrapper) > 0;
    }


}




