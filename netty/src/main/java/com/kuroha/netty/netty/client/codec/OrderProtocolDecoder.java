package com.kuroha.netty.netty.client.codec;

import com.kuroha.netty.netty.common.ResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author Chenyudeng
 */
public class OrderProtocolDecoder extends MessageToMessageDecoder<ByteBuf> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> out) throws Exception {
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.decode(byteBuf);
        out.add(responseMessage);
    }
}
