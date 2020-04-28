package com.kuroha.datastructureandalgorithm.netty.niosocket.sendpojo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author kuroha
 */
@Data
public class UserInfo implements Serializable {
    private static final long serialVersionUID = -2025727875171332527L;
    private long id;
    private String userName;
    private String password;

    public UserInfo() {
    }

    public UserInfo(long id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }
}
