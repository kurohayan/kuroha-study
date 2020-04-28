package com.kuroha.netty.netty.client;

import com.kuroha.netty.netty.client.codec.*;
import com.kuroha.netty.netty.common.RequestMessage;
import com.kuroha.netty.netty.common.order.OrderOperation;
import com.kuroha.netty.netty.util.IdUtil;
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
public class Client {

    public static void main(String[] args) throws Exception {
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.group(new NioEventLoopGroup());

        bootstrap.handler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new OrderFrameDecoder());
                pipeline.addLast(new OrderFrameEncoder());
                pipeline.addLast(new OrderProtocolEncoder());
                pipeline.addLast(new OrderProtocolDecoder());
                pipeline.addLast(new OperationToRequestMessageEncoder());
                pipeline.addLast(new LoggingHandler(LogLevel.INFO));
            }
        });

        ChannelFuture channelFuture = bootstrap.connect("127.0.0.1",8848);
        channelFuture.sync();
        OrderOperation orderOperation = new OrderOperation(1001,"tutou");
        channelFuture.channel().writeAndFlush(orderOperation);
        RequestMessage requestMessage = new RequestMessage(IdUtil.nextId(),new OrderOperation(1001,"tutou"));
        channelFuture.channel().writeAndFlush(requestMessage);
        channelFuture.channel().closeFuture().get();
    }
}
