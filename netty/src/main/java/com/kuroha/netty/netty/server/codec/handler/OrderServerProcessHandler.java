package com.kuroha.netty.netty.server.codec.handler;

import com.kuroha.netty.netty.common.Operation;
import com.kuroha.netty.netty.common.OperationResult;
import com.kuroha.netty.netty.common.RequestMessage;
import com.kuroha.netty.netty.common.ResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author Chenyudeng
 */
public class OrderServerProcessHandler extends SimpleChannelInboundHandler<RequestMessage> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage requestMessage) throws Exception {
        Operation operation = requestMessage.getMessageBody();
        OperationResult operationResult = operation.execute();
        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessageHeader(requestMessage.getMessageHeader());
        responseMessage.setMessageBody(operationResult);

        ctx.writeAndFlush(requestMessage);
    }
}
