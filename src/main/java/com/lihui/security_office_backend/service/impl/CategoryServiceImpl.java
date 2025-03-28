package com.lihui.security_office_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lihui.security_office_backend.model.entity.Category;
import com.lihui.security_office_backend.service.CategoryService;
import com.lihui.security_office_backend.mapper.CategoryMapper;
import org.springframework.stereotype.Service;

/**
* @author lihui
* @description 针对表【category(内容分类)】的数据库操作Service实现
* @createDate 2025-03-28 09:33:49
*/
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category>
    implements CategoryService {

}




