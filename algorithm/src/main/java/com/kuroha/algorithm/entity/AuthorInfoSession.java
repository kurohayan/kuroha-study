package com.kuroha.algorithm.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author kuroha
 */
@Data
public class AuthorInfoSession implements Serializable {

    private static final long serialVersionUID = 4750182487152251401L;
    private String userName;
    private long accountId;
    private String email;
    private String uuid;
    private String ip;


    public AuthorInfoSession(){

    }

    public AuthorInfoSession(String userName, long accountId, String email) {
        this.userName = userName;
        this.accountId = accountId;
        this.email = email;
    }
}
