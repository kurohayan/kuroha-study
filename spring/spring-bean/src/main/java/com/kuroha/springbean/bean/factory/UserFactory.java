package com.kuroha.springbean.bean.factory;

import com.kuroha.ioccontaineroverview.dependency.domain.User;

/**
 * {@link User} 工厂类
 * @author Chenyudeng
 */
public class UserFactory {

    public User createUser() {
        return User.createUser();
    }
}
