package com.kuroha.mq.mqserver.entity.dto;

import lombok.Data;

@Data
public class MsgDto {

    private String msgtype;
    private TextDto text;
    private AtDto at;
}
