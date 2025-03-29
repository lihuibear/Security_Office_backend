package com.lihui.security_office_backend.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lihui.security_office_backend.mapper.ScoreMapper;
import com.lihui.security_office_backend.mapper.TotalscoreMapper;
import com.lihui.security_office_backend.model.dto.score.ScoreAddRequest;
import com.lihui.security_office_backend.model.entity.Content;
import com.lihui.security_office_backend.model.entity.Score;
import com.lihui.security_office_backend.model.entity.Totalscore;
import com.lihui.security_office_backend.model.entity.User;
import com.lihui.security_office_backend.model.enums.CompletionStatusType;
import com.lihui.security_office_backend.service.ContentService;
import com.lihui.security_office_backend.service.ScoreService;
import com.lihui.security_office_backend.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Service
public class ScoreServiceImpl extends ServiceImpl<ScoreMapper, Score> implements ScoreService {

    @Resource
    private ScoreMapper scoreMapper;

    @Resource
    private ContentService contentService;

    @Resource
    private UserService userService;

    @Resource
    private TotalscoreMapper totalscoreMapper;

    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRED)
    public boolean recordLearningProgress(HttpServletRequest request, ScoreAddRequest scoreAddRequest) {
        Long contentId = scoreAddRequest.getContentId();
        Integer learningTime = scoreAddRequest.getLearningTime();
        // 1. 参数校验
        if (contentId == null || learningTime == null || learningTime < 0) {
            return false;
        }

        // 2. 校验学生和内容是否存在
        User loginUser = userService.getLoginUser(request);
        User student = loginUser;
        Content content = contentService.getById(contentId);
        if (student == null || content == null) {
            return false;
        }
        // 3. 校验内容学习时长
        int requiredLearningTime = content.getLearningTime();
        if (requiredLearningTime == 0) {
            return false;
        }

        // 4. 查询现有的分数记录
        LambdaQueryWrapper<Score> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Score::getStudentId, student.getId())
                .eq(Score::getContentId, contentId);

        Score score = scoreMapper.selectOne(wrapper);

        if (score == null) {
            // 如果记录不存在，创建新的分数记录
            score = createScoreEntity(student.getId(), contentId, learningTime, content);
            if (score == null) {
                return false;
            }
            // 插入新记录
            if (!save(score)) {
                return false;
            }
        }

        // 5. 如果学习状态已经是完成，则不再更新分数和时长
        if (score.getCompletionStatus() == CompletionStatusType.LEARNED.getValue()) {
            return true; // 学习已完成，直接返回成功
        }

        // 6. 累加学习时长
        int totalLearningTime = score.getLearningTime() + learningTime;
        score.setLearningTime(totalLearningTime);

        // 7. 如果学习时长已经满足完成条件，则直接标记为完成，不再累加分数
        if (totalLearningTime >= requiredLearningTime) {
            score.setScore(content.getScore()); // 直接设置为满分
            try {
                score.setCompletionStatus(CompletionStatusType.LEARNED.getValue());
            } catch (IllegalArgumentException e) {
                return false;
            }
        } else {
            // 累加分数
            double scorePerMinute = (double) content.getScore() / requiredLearningTime;
            int earnedScoreThisTime = (int) Math.round(Math.min(learningTime * scorePerMinute, content.getScore()));
            int totalScore = score.getScore() + earnedScoreThisTime;
            score.setScore(Math.min(totalScore, content.getScore())); // 确保总分不超过最大分数
            try {
                score.setCompletionStatus(CompletionStatusType.NOT_LEARNED.getValue());
            } catch (IllegalArgumentException e) {
                return false;
            }
        }

        // 8. 更新记录
        boolean result = updateById(score);

        if (result) {
            // 实时计算并更新用户总分数到 totalscore 表
            updateUserTotalScore(student.getId());
        }

        return result;
    }

    private Score createScoreEntity(Long studentId, Long contentId, Integer learningTime, Content content) {
        Score score = new Score();
        score.setStudentId(studentId);
        score.setContentId(contentId);
        score.setLearningTime(learningTime);
        score.setScore(0);
        try {
            score.setCompletionStatus(CompletionStatusType.NOT_LEARNED.getValue());
        } catch (IllegalArgumentException e) {
            return null;
        }
        score.setCreateTime(new Date());
        return score;
    }

    private void updateUserTotalScore(Long userId) {
        // 查询用户的所有分数记录
        LambdaQueryWrapper<Score> scoreWrapper = new LambdaQueryWrapper<>();
        scoreWrapper.eq(Score::getStudentId, userId);
        List<Score> scoreList = scoreMapper.selectList(scoreWrapper);

        int totalScore = 0;
        for (Score score : scoreList) {
            totalScore += score.getScore();
        }

        // 查询 totalscore 表中该用户的记录
        LambdaQueryWrapper<Totalscore> totalscoreWrapper = new LambdaQueryWrapper<>();
        totalscoreWrapper.eq(Totalscore::getUserId, userId);
        Totalscore totalscore = totalscoreMapper.selectOne(totalscoreWrapper);

        if (totalscore == null) {
            // 如果记录不存在，创建新记录
            totalscore = new Totalscore();
            totalscore.setUserId(userId);
            totalscore.setAllScore(totalScore);
            totalscoreMapper.insert(totalscore);
        } else {
            // 如果记录存在，更新总分数
            totalscore.setAllScore(totalScore);
            totalscoreMapper.updateById(totalscore);
        }
    }
}