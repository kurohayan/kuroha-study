package com.kuroha.springbean.bean.definition;

import com.kuroha.springbean.bean.factory.DefaultUserFactory;
import com.kuroha.springbean.bean.factory.UserFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * Bean 初始化Demo
 */
@Configuration // Configuration Class
public class BeanInitializationDemo {

    public static void main(String[] args) {
        // 初始化BeanFactroy容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //注册Configuration Class (配置类)
        applicationContext.register(BeanInitializationDemo.class);
        // 启动spring 应用上下文
        applicationContext.refresh();
        // 非延迟性初始化在 spring 应用上下文启动完成后,被初始化
        System.out.println("spring 应用上下文已启动...");
        // 依赖查找
        UserFactory userFactory = applicationContext.getBean(UserFactory.class);

        System.out.println(userFactory);
        System.out.println("spring 应用上下文准备关闭...");
        // 关闭spring应用上下文
        applicationContext.close();
        System.out.println("spring 应用上下文已关闭...");
    }

    @Bean(initMethod = "initUserFactory", destroyMethod = "doDestroy")
    @Lazy
    public UserFactory userFactory() {
        return new DefaultUserFactory();
    }

}
