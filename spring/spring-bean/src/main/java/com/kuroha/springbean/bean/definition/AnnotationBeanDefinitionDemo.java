package com.kuroha.springbean.bean.definition;

import com.kuroha.ioccontaineroverview.dependency.domain.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * @author Chenyudeng
 */
//3. 通过@Import来进行导入
@Import(AnnotationBeanDefinitionDemo.Config.class) // 通过@Import来进行导入
public class AnnotationBeanDefinitionDemo {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //注册Configuration Class (配置类)
        applicationContext.register(AnnotationBeanDefinitionDemo.class);

        //通过BeanDefinition 注册Api实现
        //1.通过命名Bean的注册方式
        registerBeanDefinition(applicationContext,"kuroha-user");
        //2.非命名Bean的注册方式
        registerBeanDefinition(applicationContext);

        // 启动Spring应用上下文
        applicationContext.refresh();

        System.out.println("Config类型的所有beans" + applicationContext.getBeansOfType(Config.class));
        System.out.println("User 类型的所有beans" + applicationContext.getBeansOfType(User.class));


        applicationContext.close();
    }

    public static void registerBeanDefinition(BeanDefinitionRegistry registry,String beanName) {
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        beanDefinitionBuilder.addPropertyValue("id",1L).addPropertyValue("name","小马");

        //判断如果beanName参数存在时

        if (StringUtils.hasText(beanName)) {
            //注册BeanDefinition
            registry.registerBeanDefinition(beanName,beanDefinitionBuilder.getBeanDefinition());
        } else {
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(),registry);
        }


    }

    public static void registerBeanDefinition(BeanDefinitionRegistry registry) {
        registerBeanDefinition(registry,null);
    }

    //2. 通过@Component 方式
    @Component  //定义当前累作为Spring Bean 组件
    public static class Config {
        //1. 通过@Bean方式定义
        @Bean(name = {"user","xiaoma-user"})
        public User user() {
            User user = new User();
            user.setId(1L);
            user.setName("小马");
            return user;
        }
    }



}
