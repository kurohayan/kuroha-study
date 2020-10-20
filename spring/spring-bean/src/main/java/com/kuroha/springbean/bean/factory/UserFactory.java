package com.kuroha.springbean.bean.factory;

import com.kuroha.ioccontaineroverview.dependency.domain.User;

/**
 * {@link User} 工厂类
 * @author Chenyudeng
 */
public interface UserFactory extends AutoCloseable {

    default User createUser() {
        return User.createUser();
    }

    void initUserFactory();

    void doDestroy();
}
