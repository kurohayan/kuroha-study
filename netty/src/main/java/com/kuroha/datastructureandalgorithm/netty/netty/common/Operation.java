package com.kuroha.datastructureandalgorithm.netty.netty.common;

/**
 * @author Chenyudeng
 */
public abstract class Operation extends MessageBody {

    public abstract OperationResult execute();
}
