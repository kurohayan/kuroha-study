package com.kuroha.springdemo.controller;

import com.kuroha.springdemo.entity.po.PayOrderType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.r2dbc.core.DatabaseClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

/**
 * @author kuroha
 */
@RestController
@RequestMapping("test")
public class TestController {

    @Resource
    private DatabaseClient databaseClient;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("gets")
    public Flux<PayOrderType> payOrderTypeFlux() {
        return databaseClient.select().from("pay_order_type").as(PayOrderType.class).fetch().all();
    }

    @GetMapping("get")
    public Mono<PayOrderType> payOrderTypeMono() {
        return databaseClient.select().from("pay_order_type").as(PayOrderType.class).fetch().first();
    }

//    @GetMapping
//    public Mono<String> redisMono() {
//        return Mono.
//    }

}
