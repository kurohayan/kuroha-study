package com.kuroha.datastructureandalgorithm.netty.netty.common.auth;

import com.kuroha.datastructureandalgirithm.study.netty.common.Operation;
import com.kuroha.datastructureandalgirithm.study.netty.common.OperationResult;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Chenyudeng
 */
@Slf4j
@Data
public class AuthOperation extends Operation {

    private final String userName;
    private final String password;

    @Override
    public OperationResult execute() {
        if ("admin".equals(this.userName)) {
            AuthOperationResult authResponse = new AuthOperationResult(true);
            return authResponse;
        }
        return new AuthOperationResult(false);
    }
}
