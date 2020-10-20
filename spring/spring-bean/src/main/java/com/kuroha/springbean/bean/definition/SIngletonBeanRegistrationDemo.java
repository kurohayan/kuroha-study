package com.kuroha.springbean.bean.definition;

import com.kuroha.springbean.bean.factory.DefaultUserFactory;
import com.kuroha.springbean.bean.factory.UserFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author kuroha
 */
public class SIngletonBeanRegistrationDemo {

    public static void main(String[] args) {
        // 初始化BeanFactroy容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        ConfigurableListableBeanFactory beanFactory = applicationContext.getBeanFactory();
        UserFactory userFactory = new DefaultUserFactory();
        beanFactory.registerSingleton("userFactory",userFactory);
        // 启动spring 应用上下文
        applicationContext.refresh();
        UserFactory bean = applicationContext.getBean("userFactory",UserFactory.class);
        System.out.println(bean == userFactory);
        // 关闭spring应用上下文
        applicationContext.close();
        // 强制触发gc
        System.gc();
    }
}
