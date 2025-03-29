package com.lihui.security_office_backend.model.dto.totalscore;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lihui.security_office_backend.common.PageRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName totalscore
 */
@Data
public class TotalscoreQuerytRequest extends PageRequest implements Serializable {
    /**
     * 
     */
    private Long id;

    /**
     * 
     */
    private Long userId;


    private static final long serialVersionUID = 1L;
}