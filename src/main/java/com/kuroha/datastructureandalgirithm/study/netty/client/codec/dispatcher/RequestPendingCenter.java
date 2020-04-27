package com.kuroha.datastructureandalgirithm.study.netty.client.codec.dispatcher;

import com.kuroha.datastructureandalgirithm.study.netty.common.OperationResult;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Chenyudeng
 */
public class RequestPendingCenter {

    private Map<Long,OperationResultFuture> map = new ConcurrentHashMap<>();

    public void add(Long streamId, OperationResultFuture future) {
        this.map.put(streamId,future);
    }

    public void set(Long streamId, OperationResult operationResult) {
        OperationResultFuture operationResultFuture = this.map.remove(streamId);
        if (operationResultFuture != null) {
            operationResultFuture.setSuccess(operationResult);
        }
    }

}
