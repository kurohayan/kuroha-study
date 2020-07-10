package com.kuroha.ioccontaineroverview.dependency.lookup;

import com.kuroha.ioccontaineroverview.dependency.annotation.Super;
import com.kuroha.ioccontaineroverview.dependency.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

/**
 * 依赖查找示例
 * 1.通过名称来查找
 * @author Chenyudeng
 */
public class DependencyLookupDemo {

    public static void main(String[] args) {
        // 配置xml 配置文件
        // 启动spring应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META_INF/dependency-lookup-context.xml");
        // 按照类型查找
        lookupInByType(beanFactory);
        // 按照类型查找集合对象
        lookupInByCollecationType(beanFactory);
        //通过注解查找
        lookupInByAnnotationType(beanFactory);
//        // 实时查找
//        lookupInRealTime(beanFactory);
//        // 延迟查找
//        lookupInLazy(beanFactory);
    }

    @SuppressWarnings("unchecked")
    private static void lookupInByAnnotationType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = (Map) listableBeanFactory.getBeansWithAnnotation(Super.class);
            System.out.println("所有标注@Super查找到的所有User集合对象" + users);
        }
    }

    private static void lookupInByCollecationType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("查找到的所有User集合对象" + users);
        }
    }

    private static void lookupInByType(BeanFactory beanFactory) {
        User user = beanFactory.getBean(User.class);
        System.out.println("实时查找" + user);
    }

    private static void lookupInLazy(BeanFactory beanFactory) {
        ObjectFactory<User> objectFactory = (ObjectFactory<User>) beanFactory.getBean("objectFactory");
        User user = objectFactory.getObject();
        System.out.println("延迟查找" + user);
    }

    private static void lookupInRealTime(BeanFactory beanFactory) {
        Object user = beanFactory.getBean("user");
        System.out.println("实时查找" + user);
    }
}
