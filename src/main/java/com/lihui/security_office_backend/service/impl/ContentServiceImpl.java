package com.lihui.security_office_backend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lihui.security_office_backend.model.entity.Content;
import com.lihui.security_office_backend.service.ContentService;
import com.lihui.security_office_backend.mapper.ContentMapper;
import org.springframework.stereotype.Service;

/**
* @author lihui
* @description 针对表【content(学习内容)】的数据库操作Service实现
* @createDate 2025-03-28 09:33:49
*/
@Service
public class ContentServiceImpl extends ServiceImpl<ContentMapper, Content>
    implements ContentService {

}




