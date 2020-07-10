package com.kuroha.ioccontaineroverview.dependency.domain;

import com.kuroha.ioccontaineroverview.dependency.annotation.Super;

/**
 * @author Chenyudeng
 */
@Super
public class SuperUser extends User {

    private String address;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "SuperUser{" +
                "address='" + address + '\'' +
                "} " + super.toString();
    }
}
