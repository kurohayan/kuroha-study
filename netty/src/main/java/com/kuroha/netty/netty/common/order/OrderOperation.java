package com.kuroha.netty.netty.common.order;

import com.kuroha.netty.netty.common.Operation;
import com.kuroha.netty.netty.common.OperationResult;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Chenyudeng
 */
@Data
@Slf4j
public class OrderOperation extends Operation {

    private int tableId;
    private String dish;

    public OrderOperation(int tableId, String dish) {
        this.tableId = tableId;
        this.dish = dish;
    }
    @Override
    public OperationResult execute() {
        log.info("order's executing startup with orderRequest: " + toString());
        log.info("order's executing complete");
        OrderOperationResult orderResponse = new OrderOperationResult(tableId,dish,true);
        return orderResponse;
    }
}