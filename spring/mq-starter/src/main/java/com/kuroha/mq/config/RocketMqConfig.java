package com.kuroha.mq.config;

import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.ons.api.order.MessageOrderListener;
import com.aliyun.openservices.ons.api.order.OrderConsumer;
import com.aliyun.openservices.ons.api.order.OrderProducer;
import com.kuroha.mq.ConditionalOnMqEnabled;
import com.kuroha.mq.MqProperties;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.HashMap;
import java.util.List;
import java.util.Properties;


/**
 * @author samtofor
 */
@Configuration
@EnableConfigurationProperties(MqProperties.class)
@ConditionalOnMqEnabled
@Order
public class RocketMqConfig {

    @Autowired
    private MqProperties mqProperties;
    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public Producer producer() {
        if (StringUtils.isBlank(mqProperties.getGroupId())) {
            return null;
        }
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.GROUP_ID,mqProperties.getGroupId());
        properties.put(PropertyKeyConst.AccessKey,mqProperties.getAccessKey());
        properties.put(PropertyKeyConst.SecretKey,mqProperties.getAccessSecret());
        properties.put(PropertyKeyConst.NAMESRV_ADDR,mqProperties.getServerAddr());
        Producer producer = ONSFactory.createProducer(properties);
        producer.start();
        return producer;
    }

    @Bean
    public Consumer consumer() {
        if (StringUtils.isBlank(mqProperties.getGroupId())) {
            return null;
        }
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.GROUP_ID,mqProperties.getGroupId());
        properties.put(PropertyKeyConst.AccessKey,mqProperties.getAccessKey());
        properties.put(PropertyKeyConst.SecretKey,mqProperties.getAccessSecret());
        properties.put(PropertyKeyConst.NAMESRV_ADDR,mqProperties.getServerAddr());
        properties.put(PropertyKeyConst.ConsumeThreadNums,Runtime.getRuntime().availableProcessors() * 2);
        properties.put(PropertyKeyConst.ConsumeTimeout,10000);
        Consumer consumer = ONSFactory.createConsumer(properties);
        List<HashMap<String, String>> subscribe = mqProperties.getSubscribe();
        if (subscribe != null && subscribe.size() != 0) {
            for (HashMap<String, String> map : subscribe) {
                String topic = map.get("topic");
                String tag = map.get("tag");
                String listenerClassName = map.get("listenerClassName");
                MessageListener messageListener = applicationContext.getBean(listenerClassName, MessageListener.class);
                consumer.subscribe(topic, tag, messageListener);
            }
            consumer.start();
        }
        return consumer;
    }

    @Bean
    public OrderProducer orderProducer() {
        if (StringUtils.isBlank(mqProperties.getOrderGroupId())) {
            return null;
        }
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.GROUP_ID,mqProperties.getOrderGroupId());
        properties.put(PropertyKeyConst.AccessKey,mqProperties.getAccessKey());
        properties.put(PropertyKeyConst.SecretKey,mqProperties.getAccessSecret());
        properties.put(PropertyKeyConst.NAMESRV_ADDR,mqProperties.getServerAddr());
        OrderProducer orderProducer = ONSFactory.createOrderProducer(properties);
        orderProducer.start();
        return orderProducer;
    }

    @Bean
    public OrderConsumer orderConsumer() {
        if (StringUtils.isBlank(mqProperties.getOrderGroupId())) {
            return null;
        }
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.GROUP_ID,mqProperties.getOrderGroupId());
        properties.put(PropertyKeyConst.AccessKey,mqProperties.getAccessKey());
        properties.put(PropertyKeyConst.SecretKey,mqProperties.getAccessSecret());
        properties.put(PropertyKeyConst.NAMESRV_ADDR,mqProperties.getServerAddr());
        properties.put(PropertyKeyConst.ConsumeThreadNums,Runtime.getRuntime().availableProcessors() * 2);
        properties.put(PropertyKeyConst.ConsumeTimeout,10000);
        OrderConsumer orderedConsumer = ONSFactory.createOrderedConsumer(properties);
        List<HashMap<String, String>> subscribe = mqProperties.getOrderSubscribe();
        if (subscribe != null && subscribe.size() != 0) {
            for (HashMap<String, String> map : subscribe) {
                String topic = map.get("topic");
                String tag = map.get("tag");
                String listenerClassName = map.get("listenerClassName");
                MessageOrderListener messageListener = applicationContext.getBean(listenerClassName, MessageOrderListener.class);
                orderedConsumer.subscribe(topic, tag, messageListener);
            }
            orderedConsumer.start();
        }
        return orderedConsumer;
    }

}
