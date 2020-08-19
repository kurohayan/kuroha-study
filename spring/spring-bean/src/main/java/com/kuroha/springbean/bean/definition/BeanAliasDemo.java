package com.kuroha.springbean.bean.definition;

import com.kuroha.ioccontaineroverview.dependency.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanAliasDemo {

    public static void main(String[] args) {
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-definition-context.xml");
        //通过别名 获取曾用名user
        User user = beanFactory.getBean("user", User.class);
        User xiaomaUser = beanFactory.getBean("xiaoma-user", User.class);
        System.out.println(user==xiaomaUser);

    }

}