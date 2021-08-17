package com.kuroha.mq.entity.dto;

import lombok.Data;

import java.util.List;

@Data
public class AtDto {
    private List<String> atMobiles;
    private Boolean atAll = false;

}
