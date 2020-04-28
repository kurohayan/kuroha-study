package com.kuroha.datastructureandalgorithm.netty.netty.common;

/**
 * @author Chenyudeng
 */
public class RequestMessage extends Message<Operation> {
    @Override
    public Class getMessageBodyDecodeClass(int opcode) {
        return OperationType.fromOpcode(opcode).getOperationClazz();
    }

    public RequestMessage(){}

    public RequestMessage(Long streamId, Operation operation) {
        MessageHeader messageHeader = new MessageHeader();
        messageHeader.setStreamId(streamId);
        messageHeader.setOpCode(OperationType.fromOperation(operation).getOpCode());
        this.setMessageHeader(messageHeader);
        this.setMessageBody(operation);
    }

}
