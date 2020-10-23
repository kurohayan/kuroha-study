package com.kuroha.springdemo.controller;

import com.kuroha.springdemo.entity.po.PayOrderType;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.annotation.Resource;

/**
 * @author kuroha
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Resource
    private DatabaseClient databaseClient;

    @GetMapping("get")
    public Flux<PayOrderType> payOrderTypeFlux() {
        return databaseClient.select().from("pay_order_type").as(PayOrderType.class).fetch().all();
    }

}
