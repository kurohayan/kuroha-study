package com.kuroha.datastructureandalgorithm.netty.netty.common.auth;

import com.kuroha.datastructureandalgirithm.study.netty.common.OperationResult;
import lombok.Data;

/**
 * @author Chenyudeng
 */
@Data
public class AuthOperationResult extends OperationResult {

    private final boolean passAuth;
}
