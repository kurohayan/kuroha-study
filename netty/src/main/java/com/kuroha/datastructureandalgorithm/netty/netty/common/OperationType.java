package com.kuroha.datastructureandalgorithm.netty.netty.common;

import com.kuroha.datastructureandalgirithm.study.netty.common.auth.AuthOperation;
import com.kuroha.datastructureandalgirithm.study.netty.common.auth.AuthOperationResult;
import com.kuroha.datastructureandalgirithm.study.netty.common.keepalive.KeepaliveOperation;
import com.kuroha.datastructureandalgirithm.study.netty.common.keepalive.KeepaliveOperationResult;
import com.kuroha.datastructureandalgirithm.study.netty.common.order.OrderOperation;
import com.kuroha.datastructureandalgirithm.study.netty.common.order.OrderOperationResult;

/**
 * @author Chenyudeng
 */
public enum OperationType {

    AUTH(1, AuthOperation.class, AuthOperationResult.class),
    KEEPALIVE(2, KeepaliveOperation.class, KeepaliveOperationResult.class),
    ORDER(3, OrderOperation.class, OrderOperationResult.class);

    private int opCode;
    private Class<? extends Operation> operationClazz;
    private Class<? extends OperationResult> operationResultClazz;

    OperationType(int opCode,Class<? extends Operation> operationClazz,Class<? extends OperationResult> operationResultClazz) {
        this.opCode = opCode;
        this.operationClazz = operationClazz;
        this.operationResultClazz = operationResultClazz;
    }

    public static OperationType fromOpcode(int opcode) {
        if (opcode == AUTH.opCode) {
            return AUTH;
        } else if (opcode == KEEPALIVE.opCode) {
            return KEEPALIVE;
        } else if (opcode == ORDER.opCode) {
            return ORDER;
        } else {
            return null;
        }
    }

    public static OperationType fromOperation(Operation operation) {
        if (operation.getClass() == AUTH.operationClazz) {
            return AUTH;
        } else if (operation.getClass() == KEEPALIVE.operationClazz) {
            return KEEPALIVE;
        } else if (operation.getClass() == ORDER.operationClazz) {
            return ORDER;
        } else {
            return null;
        }
    }

    public int getOpCode() {
        return opCode;
    }

    public Class<? extends Operation> getOperationClazz() {
        return operationClazz;
    }
    public Class<? extends OperationResult> getOperationResultClazz() {
        return operationResultClazz;
    }

}
