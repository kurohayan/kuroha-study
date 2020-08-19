package com.kuroha.springbean.bean.definition;

import com.kuroha.ioccontaineroverview.dependency.domain.User;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.GenericBeanDefinition;

/**
 * {@link org.springframework.beans.factory.config.BeanDefinition} 构建示例
 * @author Chenyudeng
 */
public class BeanDefinitionCreationDemo {

    public static void main(String[] args) {
        // 1.通过BeanDefinitionBuilder创建
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(User.class);
        // 设置属性
        beanDefinitionBuilder.addPropertyValue("id",1)
                .addPropertyValue("name","小马");
        // 获取BeanDefinition 对象
        AbstractBeanDefinition beanDefinition = beanDefinitionBuilder.getBeanDefinition();
        // 获取BeanDefinition并不是最终bean对象,可以修改
        // 2.通过AbstractBeanDefinition以其派生类
        GenericBeanDefinition genericBeanDefinition = new GenericBeanDefinition();
        // 通过 MutablePropertyValues 批量操作属性
        MutablePropertyValues mutablePropertyValues = new MutablePropertyValues();
        mutablePropertyValues.add("id",1)
                .add("name","小马");
        // 通过 set MutablePropertyValues 批量操作属性
        genericBeanDefinition.setPropertyValues(mutablePropertyValues);
    }

}
