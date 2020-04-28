package com.kuroha.netty.netty.common.order;

import com.kuroha.netty.netty.common.OperationResult;
import lombok.Data;

/**
 * @author Chenyudeng
 */
@Data
public class OrderOperationResult extends OperationResult {

    private final int tableId;
    private final String dish;
    private final boolean flag;
}
