package com.kuroha.mq.entity.dto;

import lombok.Data;

@Data
public class MsgDto {

    private String msgtype;
    private TextDto text;
    private AtDto at;
}
