package com.kuroha.datastructureandalgirithm.study.niosocket.channel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author kuroha
 */
public class Client1 {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",8848));
        while (!socketChannel.finishConnect()) {

        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.put("我是测试数据".getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        byteBuffer.clear();
        socketChannel.read(byteBuffer);
        System.out.println(new String(byteBuffer.array(),0,byteBuffer.position()));
        byteBuffer.clear();
        byteBuffer.put("收到".getBytes());
        socketChannel.write(byteBuffer);
        socketChannel.close();
    }

}
