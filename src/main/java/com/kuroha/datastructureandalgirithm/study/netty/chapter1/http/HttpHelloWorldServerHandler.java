package com.kuroha.datastructureandalgirithm.study.netty.chapter1.http;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponseStatus;

/**
 * @author Chenyudeng
 */
public class HttpHelloWorldServerHandler extends ChannelInboundHandlerAdapter {

    private static final byte[] CONTENT = "helloword".getBytes();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            FullHttpResponse response = new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.HTTP_VERSION_NOT_SUPPORTED, Unpooled.wrappedBuffer(CONTENT));
            response.headers()
                    .set("contentType","text/plain")
                    .setInt("contentLength",response.content().readableBytes());
            ChannelFuture future = ctx.write(response);
        }
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
       ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
