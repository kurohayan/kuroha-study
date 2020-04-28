package com.kuroha.netty.netty.common.keepalive;

import com.kuroha.datastructureandalgirithm.study.netty.common.Operation;
import com.kuroha.datastructureandalgirithm.study.netty.common.OperationResult;

/**
 * @author Chenyudeng
 */
public class KeepaliveOperation  extends Operation {

    private long time;

    public KeepaliveOperation() {
        this.time = System.nanoTime();
    }

    @Override
    public OperationResult execute() {
        KeepaliveOperationResult keepaliveResponse = new KeepaliveOperationResult(time);
        return keepaliveResponse;
    }
}