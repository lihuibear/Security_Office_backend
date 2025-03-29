package com.lihui.security_office_backend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lihui.security_office_backend.model.dto.score.ScoreAddRequest;
import com.lihui.security_office_backend.model.entity.Score;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;

/**
* @author lihui
* @description 针对表【score(学生分数)】的数据库操作Service
* @createDate 2025-03-28 09:33:49
*/
public interface ScoreService extends IService<Score> {
//    boolean recordLearningProgress(Long contentId, Integer learningTime);

    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    boolean recordLearningProgress(HttpServletRequest request, ScoreAddRequest scoreAddRequest);
}
