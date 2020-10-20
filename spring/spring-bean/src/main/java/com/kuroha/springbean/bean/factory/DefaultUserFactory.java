package com.kuroha.springbean.bean.factory;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * 默认实现 {@link UserFactory}
 */
public class DefaultUserFactory implements UserFactory, InitializingBean , DisposableBean {

    // 1. 基于@PostConstruct注解
    @PostConstruct
    public void init() {
        System.out.println("@PostConstruct : UserFactroy 初始化中...");
    }

    @Override
    public void initUserFactory() {
        System.out.println("自定义初始化方法initUserFactory() : UserFactroy 初始化中...");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean#afterPropertiesSet() : UserFactroy 初始化中...");
    }

    @PreDestroy
    public void preDestory() {
        System.out.println("@PreDestroy : UserFactroy 销毁中...");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean#destroy() : UserFactroy 销毁中...");
    }

    @Override
    public void doDestroy() {
        System.out.println("自定义消费方法doDestroy() : UserFactroy 销毁中...");
    }

    @Override
    public void finalize() throws Throwable {
        System.out.println("当前DefaultFactory 对象正在被垃圾回收");
    }

    @Override
    public void close() throws Exception {
        System.out.println("当前DefaultFactory 对象正在被垃圾回收 调用方法AutoCloseable#close()");
    }
}
