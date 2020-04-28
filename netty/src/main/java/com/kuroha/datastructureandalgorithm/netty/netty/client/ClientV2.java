package com.kuroha.datastructureandalgorithm.netty.netty.client;

import com.kuroha.datastructureandalgirithm.study.netty.client.codec.dispatcher.OperationResultFuture;
import com.kuroha.datastructureandalgirithm.study.netty.client.codec.dispatcher.RequestPendingCenter;
import com.kuroha.datastructureandalgirithm.study.netty.client.codec.dispatcher.ResponseDispatcherHandler;
import com.kuroha.datastructureandalgirithm.study.netty.common.OperationResult;
import com.kuroha.datastructureandalgirithm.study.netty.common.RequestMessage;
import com.kuroha.datastructureandalgirithm.study.netty.common.order.OrderOperation;
import com.kuroha.datastructureandalgirithm.study.netty.util.IdUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author Chenyudeng
 */
public class ClientV2 {

    public static void main(String[] args) throws Exception {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(new NioEventLoopGroup());
        RequestPendingCenter requestPendingCenter = new RequestPendingCenter();

        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new OrderFrameDecoder());
                pipeline.addLast(new OrderFrameEncoder());
                pipeline.addLast(new OrderProtocolEncoder());
                pipeline.addLast(new OrderProtocolDecoder());
                pipeline.addLast(new ResponseDispatcherHandler(requestPendingCenter));
                pipeline.addLast(new OperationToRequestMessageEncoder());
                pipeline.addLast(new LoggingHandler(LogLevel.INFO));
            }
        });

        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",8848);
        channelFuture.sync();

        long streamId = IdUtil.nextId();
        RequestMessage requestMessage = new RequestMessage(streamId,new OrderOperation(1001,"tutou"));

        OperationResultFuture operationResultFuture = new OperationResultFuture();
        requestPendingCenter.add(streamId,operationResultFuture);
        channelFuture.channel().writeAndFlush(requestMessage);
        OperationResult operationResult = operationResultFuture.get();

        System.out.println(operationResult);
        channelFuture.channel().closeFuture().get();
    }
}
