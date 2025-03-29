package com.lihui.security_office_backend.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lihui.security_office_backend.common.BaseResponse;
import com.lihui.security_office_backend.model.dto.totalscore.TotalscoreQuerytRequest;
import com.lihui.security_office_backend.model.entity.Category;
import com.lihui.security_office_backend.model.entity.Totalscore;
import com.baomidou.mybatisplus.extension.service.IService;
import com.lihui.security_office_backend.model.vo.TotalscoreVO;

import java.util.List;

/**
 * @author lihui
 * @description 针对表【totalscore】的数据库操作Service
 * @createDate 2025-03-29 15:54:36
 */
public interface TotalscoreService extends IService<Totalscore> {

    /**
     * 根据userid查询总分
     */
    BaseResponse<TotalscoreVO> selectTotalScoreByUserId(Integer userId);

    List<TotalscoreVO> getScoreList();

    QueryWrapper<Totalscore> getQueryWrapper(TotalscoreQuerytRequest totalscoreQuerytRequest);
}
