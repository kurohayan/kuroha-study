package com.kuroha.netty.netty.client.codec;

import com.kuroha.netty.netty.common.Operation;
import com.kuroha.netty.netty.common.RequestMessage;
import com.kuroha.netty.netty.util.IdUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author Chenyudeng
 */
public class OperationToRequestMessageEncoder extends MessageToMessageEncoder<Operation> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Operation operation, List<Object> out) throws Exception {
        RequestMessage requestMessage = new RequestMessage(IdUtil.nextId(),operation);
        out.add(requestMessage);
    }
}
