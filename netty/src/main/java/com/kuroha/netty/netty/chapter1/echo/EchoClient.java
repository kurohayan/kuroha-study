package com.kuroha.netty.netty.chapter1.echo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @author kuorha
 */
public class EchoClient {

    public static void main(String[] args) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY,true)
                    .handler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel channel) {
                            ChannelPipeline channelPipeline = channel.pipeline();
                            channelPipeline.addLast(new LoggingHandler(LogLevel.INFO));
                            channelPipeline.addLast(new EchoClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect("127.0.0.1",8848).sync();
            future.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully();
        }
    }

}
