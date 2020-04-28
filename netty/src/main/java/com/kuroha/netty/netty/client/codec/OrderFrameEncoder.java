package com.kuroha.netty.netty.client.codec;

import io.netty.handler.codec.LengthFieldPrepender;

/**
 * @author Chenyudeng
 */
public class OrderFrameEncoder extends LengthFieldPrepender {

    public OrderFrameEncoder() {
        super(2);
    }
}
