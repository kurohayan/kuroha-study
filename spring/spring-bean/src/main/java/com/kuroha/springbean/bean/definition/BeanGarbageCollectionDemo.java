package com.kuroha.springbean.bean.definition;

import com.kuroha.springbean.bean.factory.DefaultUserFactory;
import com.kuroha.springbean.bean.factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * @author kuroha
 */
@Configuration
public class BeanGarbageCollectionDemo {

    public static void main(String[] args) throws Exception {
        // 初始化BeanFactroy容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //注册Configuration Class (配置类)
        applicationContext.register(BeanGarbageCollectionDemo.class);
        // 启动spring 应用上下文
        applicationContext.refresh();
        UserFactory bean = applicationContext.getBean(UserFactory.class);
        // 关闭spring应用上下文
        applicationContext.close();
        // 强制触发gc
        System.gc();
        Thread.sleep(5000);
    }

    @Bean(initMethod = "initUserFactory", destroyMethod = "doDestroy")
    public UserFactory userFactory() {
        return new DefaultUserFactory();
    }

}
