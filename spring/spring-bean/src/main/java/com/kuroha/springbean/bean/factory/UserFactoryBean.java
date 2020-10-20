package com.kuroha.springbean.bean.factory;

import com.kuroha.ioccontaineroverview.dependency.domain.User;
import org.springframework.beans.factory.FactoryBean;

/**
 * {@link com.kuroha.ioccontaineroverview.dependency.domain.User} Bean {@link org.springframework.beans.factory.FactoryBean}的实现
 */
public class UserFactoryBean implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return User.createUser();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
