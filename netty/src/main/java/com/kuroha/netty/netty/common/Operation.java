package com.kuroha.netty.netty.common;

/**
 * @author Chenyudeng
 */
public abstract class Operation extends MessageBody {

    public abstract OperationResult execute();
}
