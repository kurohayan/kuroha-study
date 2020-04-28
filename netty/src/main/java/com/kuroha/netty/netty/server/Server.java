package com.kuroha.netty.netty.server;

import com.kuroha.datastructureandalgirithm.study.netty.server.codec.OrderFrameDecoder;
import com.kuroha.datastructureandalgirithm.study.netty.server.codec.OrderFrameEncoder;
import com.kuroha.datastructureandalgirithm.study.netty.server.codec.OrderProtocolDecoder;
import com.kuroha.datastructureandalgirithm.study.netty.server.codec.OrderProtocolEncoder;
import com.kuroha.datastructureandalgirithm.study.netty.server.codec.handler.OrderServerProcessHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioChannelOption;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author Chenyudeng
 */
public class Server {

    public static void main(String[] args) throws Exception {
        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
        serverBootstrap.group(new NioEventLoopGroup());
        serverBootstrap.childOption(NioChannelOption.TCP_NODELAY,true);
        serverBootstrap.option(NioChannelOption.SO_BACKLOG,1024);

        serverBootstrap.childHandler(new ChannelInitializer<NioSocketChannel>() {
            @Override
            protected void initChannel(NioSocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new OrderFrameDecoder());
                pipeline.addLast(new OrderFrameEncoder());
                pipeline.addLast(new OrderProtocolEncoder());
                pipeline.addLast(new OrderProtocolDecoder());
                pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                pipeline.addLast(new OrderServerProcessHandler());
            }
        });

        ChannelFuture channelFuture = serverBootstrap.bind(8848).sync();
        channelFuture.channel().closeFuture().get();
    }
}
