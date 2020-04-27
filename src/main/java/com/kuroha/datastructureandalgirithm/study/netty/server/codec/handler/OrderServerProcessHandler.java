package com.kuroha.datastructureandalgirithm.study.netty.server.codec.handler;

import com.kuroha.datastructureandalgirithm.study.netty.common.Operation;
import com.kuroha.datastructureandalgirithm.study.netty.common.OperationResult;
import com.kuroha.datastructureandalgirithm.study.netty.common.RequestMessage;
import com.kuroha.datastructureandalgirithm.study.netty.common.ResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

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
