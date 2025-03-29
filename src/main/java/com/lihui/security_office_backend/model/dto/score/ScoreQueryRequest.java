package com.lihui.security_office_backend.model.dto.score;

import com.lihui.security_office_backend.common.PageRequest;
import lombok.Data;

import java.io.Serializable;

/**
 * 学生分数
 * @TableName score
 */
@Data
public class ScoreQueryRequest extends PageRequest implements Serializable {




    private static final long serialVersionUID = 1L;


}