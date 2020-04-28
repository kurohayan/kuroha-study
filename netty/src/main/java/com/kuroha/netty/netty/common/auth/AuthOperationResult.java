package com.kuroha.netty.netty.common.auth;

import com.kuroha.netty.netty.common.OperationResult;
import lombok.Data;

/**
 * @author Chenyudeng
 */
@Data
public class AuthOperationResult extends OperationResult {

    private final boolean passAuth;
}
