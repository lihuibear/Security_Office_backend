package com.lihui.security_office_backend.controller;

import com.lihui.security_office_backend.common.BaseResponse;
import com.lihui.security_office_backend.common.ResultUtils;
import com.lihui.security_office_backend.model.dto.score.ScoreAddRequest;
import com.lihui.security_office_backend.service.ScoreService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/score")
public class ScoreController {

    @Resource
    private ScoreService scoreService;

    @PostMapping("/record")
    public BaseResponse<Boolean> recordLearningProgress(
            HttpServletRequest request,
            @RequestBody ScoreAddRequest scoreAddRequest) {
        boolean result = scoreService.recordLearningProgress(request, scoreAddRequest);
        return ResultUtils.success(result);
    }
}    