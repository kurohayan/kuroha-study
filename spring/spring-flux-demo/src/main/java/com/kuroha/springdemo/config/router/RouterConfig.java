package com.kuroha.springdemo.config.router;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;

@Configuration
public class RouterConfig {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Bean
    public HandlerFunction<ServerResponse> timeFunction() {
        return serverRequest -> ServerResponse.ok()
                .contentType(MediaType.TEXT_PLAIN).body(Mono.just(LocalDateTime.now().toLocalTime().toString()),String.class);
    }

    @Bean
    public HandlerFunction<ServerResponse> dateFunction() {
        return serverRequest -> ServerResponse.ok()
                .contentType(MediaType.TEXT_PLAIN).body(Mono.just(LocalDateTime.now().toLocalTime().toString()),String.class);
    }

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        RouterFunction<ServerResponse> routerFunction = RouterFunctions.route(GET("/time"),timeFunction())
                .andRoute(GET("/date"),dateFunction());
        return routerFunction;
    }

}
