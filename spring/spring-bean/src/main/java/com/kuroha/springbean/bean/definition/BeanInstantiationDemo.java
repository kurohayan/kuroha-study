package com.kuroha.springbean.bean.definition;

import com.kuroha.ioccontaineroverview.dependency.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * bean实例化
 *
 * @author Chenyudeng
 */
public class BeanInstantiationDemo {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-instantiation-context.xml");
        User user = beanFactory.getBean("user-bu-static-method", User.class);
        System.out.println(user);

    }

}
