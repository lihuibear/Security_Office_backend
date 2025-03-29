package com.lihui.security_office_backend.model.vo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName totalscore
 */

@Data
public class TotalscoreVO implements Serializable {

    /**
     * 
     */
    private Long userId;

    /**
     * 
     */
    private Integer allScore;


    private static final long serialVersionUID = 1L;
}