package com.kuroha.datastructureandalgirithm.study.netty.client.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @author Chenyudeng
 */
public class OrderFrameDecoder extends LengthFieldBasedFrameDecoder {
    public OrderFrameDecoder() {
        super(Integer.MAX_VALUE, 0, 2, 0, 2);
    }
}
