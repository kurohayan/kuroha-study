package com.kuroha.mq.mqserver;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author samtofor
 * @date
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@ConditionalOnProperty(value = "com.kuroha.mq.enabled", matchIfMissing = true)
public @interface ConditionalOnMqEnabled {

}
