package com.lihui.security_office_backend.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lihui.security_office_backend.annotation.AuthCheck;
import com.lihui.security_office_backend.common.BaseResponse;
import com.lihui.security_office_backend.common.ResultUtils;
import com.lihui.security_office_backend.constant.UserConstant;
import com.lihui.security_office_backend.model.dto.totalscore.TotalscoreQuerytRequest;
import com.lihui.security_office_backend.model.entity.Totalscore;
import com.lihui.security_office_backend.model.vo.TotalscoreVO;
import com.lihui.security_office_backend.service.TotalscoreService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/totalScore")
public class TotalScoreController {

    @Resource
    private TotalscoreService totalscoreService;

    /**
     * 根据用户 ID 查询总分数信息
     *
     * @param userId 用户 ID
     * @return 总分数信息
     */
    @GetMapping("/{userId}")
    public BaseResponse<BaseResponse<TotalscoreVO>> selectTotalScoreByUserId(@PathVariable Integer userId) {
        BaseResponse<TotalscoreVO> totalscoreVOBaseResponse = totalscoreService.selectTotalScoreByUserId(userId);
        return ResultUtils.success(totalscoreVOBaseResponse);
    }


    /**
     * 获取积分list
     */
    @GetMapping("/list")
    public BaseResponse<List<TotalscoreVO>> getTotalScoreList() {
        List<TotalscoreVO> totalscoreVOBaseResponse = totalscoreService.getScoreList();
        return ResultUtils.success(totalscoreVOBaseResponse);
    }

    /**
     * 获取积分list分页
     */
    @PostMapping("/list/page")
    public BaseResponse<Page<Totalscore>> getTotalScoreListPage(@RequestBody TotalscoreQuerytRequest totalscoreQuerytRequest) {
        long current = totalscoreQuerytRequest.getCurrent();
        long size = totalscoreQuerytRequest.getPageSize();
        // 查数据库
        Page<Totalscore> totalScorePage = totalscoreService.page(new Page<>(current, size),
                totalscoreService.getQueryWrapper(totalscoreQuerytRequest));
        return ResultUtils.success(totalScorePage);

    }


}

