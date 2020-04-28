package com.kuroha.netty.netty.common.keepalive;

import com.kuroha.netty.netty.common.Operation;
import com.kuroha.netty.netty.common.OperationResult;
import com.kuroha.netty.netty.common.Operation;

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