package com.kuroha.ioccontaineroverview.dependency.injection;

import com.kuroha.ioccontaineroverview.dependency.annotation.Super;
import com.kuroha.ioccontaineroverview.dependency.domain.User;
import com.kuroha.ioccontaineroverview.dependency.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

import java.util.Map;

/**
 * 依赖注入示例
 *
 * @author Chenyudeng
 */
public class DependencyInjectionDemo {


    public static void main(String[] args) {
        // 配置xml 配置文件
        // 启动spring应用上下文
//        BeanFactory applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");
        // 依赖来源一: 自定义的bean
        UserRepository bean = applicationContext.getBean(UserRepository.class);
        // 依赖注入
        System.out.println(bean.getBeanFactory() );
        System.out.println(bean.getBeanFactory().getBean(UserRepository.class) );
        // 依赖来源二:依赖注入(内建依赖)
//        System.out.println(bean.getBeanFactory() == applicationContext);


        ObjectFactory objectFactory = bean.getObjectFactory();
        System.out.println(objectFactory.getObject() == applicationContext);

//        // 依赖查找(错误)
//        System.out.println(applicationContext.getBean(BeanFactory.class));
        // 依赖来源三:容器内建bean
        Environment environment = applicationContext.getBean(Environment.class);
        System.out.println("获取Environment类型的Bean:" + environment);
    }

    private static void whoIsIocContainer(UserRepository userRepository, ApplicationContext applicationContext) {
        // 这个表达式为什么不会成立
        System.out.println(userRepository.getBeanFactory() == applicationContext);

        // ApplictionContext is BeanFactory
        String str= "st";

    }

    public static int reverse(int x) {
        int num;
        int sum = 0;
        boolean flag = false;
        if (x == -Integer.MAX_VALUE) {
            return 0;
        }
        if(x < 0) {
            x = -x;
            flag = true;
        }
        while(x!=0) {
            sum*=10;
            num = x%10;
            x /= 10;
            sum += num;
        }
        if (flag) {
            sum = -sum;
        }
        return sum;
    }


}
