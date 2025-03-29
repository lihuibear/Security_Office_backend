package com.lihui.security_office_backend.service.impl;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lihui.security_office_backend.common.BaseResponse;
import com.lihui.security_office_backend.common.ResultUtils;
import com.lihui.security_office_backend.exception.BusinessException;
import com.lihui.security_office_backend.exception.ErrorCode;
import com.lihui.security_office_backend.model.dto.totalscore.TotalscoreQuerytRequest;
import com.lihui.security_office_backend.model.entity.Totalscore;
import com.lihui.security_office_backend.mapper.TotalscoreMapper;
import com.lihui.security_office_backend.model.vo.TotalscoreVO;
import com.lihui.security_office_backend.service.TotalscoreService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lihui
 * @description 针对表【totalscore】的数据库操作Service实现
 * @createDate 2025-03-29 15:54:36
 */
@Service
public class TotalscoreServiceImpl extends ServiceImpl<TotalscoreMapper, Totalscore>
        implements TotalscoreService {
    @Resource
    private TotalscoreMapper totolscoreMapper;

    @Override
    public BaseResponse<TotalscoreVO> selectTotalScoreByUserId(Integer userId) {


        // 校验
        if (userId == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "用户 ID 不能为空");
        }
        // 查询
        Totalscore totalscore = this.baseMapper.selectOne(new LambdaQueryWrapper<Totalscore>().eq(Totalscore::getUserId, userId));
        if (totalscore == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "用户积分记录不存在");
        }
        TotalscoreVO totalscoreVO = new TotalscoreVO();
        BeanUtils.copyProperties(totalscore, totalscoreVO);
        return ResultUtils.success(totalscoreVO);
    }

    @Override
    public List<TotalscoreVO> getScoreList() {
        // 查询所有积分记录
        List<Totalscore> totalscoreList = totolscoreMapper.selectList(null);
        // 转换为 TotalscoreVO 列表
        return totalscoreList.stream()
                .map(totalscore -> {
                    TotalscoreVO totalscoreVO = new TotalscoreVO();
                    BeanUtils.copyProperties(totalscore, totalscoreVO);
                    return totalscoreVO;
                }).collect(Collectors.toList());
    }

    @Override
    public QueryWrapper<Totalscore> getQueryWrapper(TotalscoreQuerytRequest totalscoreQuerytRequest) {
        // 创建 QueryWrapper 对象
        QueryWrapper<Totalscore> queryWrapper = new QueryWrapper<>();
        if (totalscoreQuerytRequest == null) {
            return queryWrapper;
        }

        Long id = totalscoreQuerytRequest.getId();
        Long userId = totalscoreQuerytRequest.getUserId();

        String sortField = totalscoreQuerytRequest.getSortField();
        String sortOrder = totalscoreQuerytRequest.getSortOrder();

        // 拼接查询条件
        queryWrapper.eq(ObjUtil.isNotEmpty(id), "id", id);
        queryWrapper.eq(ObjUtil.isNotEmpty(userId), "userId", userId);
        // 排序
        queryWrapper.orderBy(StrUtil.isNotEmpty(sortField), sortOrder.equals("ascend"), sortField);
        return queryWrapper;

    }

}