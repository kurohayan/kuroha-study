package com.kuroha.netty.netty.common;

/**
 * @author Chenyudeng
 */
public class ResponseMessage extends Message<OperationResult> {
    @Override
    public Class getMessageBodyDecodeClass(int opcode) {
        return OperationType.fromOpcode(opcode).getOperationResultClazz();
    }
}
