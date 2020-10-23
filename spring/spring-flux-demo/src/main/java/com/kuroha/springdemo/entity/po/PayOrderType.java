package com.kuroha.springdemo.entity.po;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author kuroha
 */
@Data
public class PayOrderType implements Serializable {

    private static final long serialVersionUID = -2074211080904836603L;

    private Long id;

    private String memo;

    private Double fee;

    private Integer status;

    private Integer type;

    private String typeMemo;

    private LocalDateTime createTime;

    private LocalDateTime modifyTime;

}