package com.kuroha.datastructureandalgirithm.study.netty.client.codec;

import com.kuroha.datastructureandalgirithm.study.netty.common.RequestMessage;
import com.kuroha.datastructureandalgirithm.study.netty.common.ResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author Chenyudeng
 */
public class OrderProtocolEncoder extends MessageToMessageEncoder<RequestMessage> {

    @Override
    protected void encode(ChannelHandlerContext ctx, RequestMessage requestMessage, List<Object> out) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();
        requestMessage.encode(buffer);

        out.add(buffer);
    }
}
