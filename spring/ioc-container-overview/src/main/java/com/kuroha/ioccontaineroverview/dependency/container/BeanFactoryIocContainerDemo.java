package com.kuroha.ioccontaineroverview.dependency.container;

import com.kuroha.ioccontaineroverview.dependency.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;

import java.util.Map;

/**
 * @author Chenyudeng
 */
public class BeanFactoryIocContainerDemo {

    public static void main(String[] args) {
        // 创建beanFactory容器
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        //xml配置文件用于加载配置
        // 加载配置
        int i = reader.loadBeanDefinitions("classpath:/META_INF/dependency-injection-context.xml");
        System.out.println("bean 定义加载的数量:" + i);
        // 依爱查找集合对象
        lookupInByCollecationType(beanFactory);
    }

    private static void lookupInByCollecationType(BeanFactory beanFactory) {
        if (beanFactory instanceof ListableBeanFactory){
            ListableBeanFactory listableBeanFactory = (ListableBeanFactory) beanFactory;
            Map<String, User> users = listableBeanFactory.getBeansOfType(User.class);
            System.out.println("查找到的所有User集合对象" + users);
        }
    }
}
