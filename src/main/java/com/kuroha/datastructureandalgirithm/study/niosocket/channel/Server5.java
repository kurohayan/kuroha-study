package com.kuroha.datastructureandalgirithm.study.niosocket.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * @author kuroha
 */
public class Server5 {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8848));
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true) {
            int selectCount = selector.select();
            Server5.accept(selector);
        }
    }

    private static void accept(Selector selector) throws IOException {
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1000);
        for (SelectionKey selectionKey : selectionKeys) {
            if (selectionKey.isAcceptable()) {
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                SocketChannel socketChannel = serverSocketChannel.accept();
                socketChannel.configureBlocking(false);
                socketChannel.register(selector,SelectionKey.OP_WRITE);
//                socketChannel.register(selector,SelectionKey.OP_READ);
                System.out.println(socketChannel.isBlocking());
                int readLength = socketChannel.read(byteBuffer);
                System.out.println(new String(byteBuffer.array(),0,byteBuffer.position()));
                byteBuffer.clear();
            }
            if (selectionKey.isReadable()) {
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                SocketChannel socketChannel = serverSocketChannel.accept();
                int readLength = socketChannel.read(byteBuffer);
                System.out.println(new String(byteBuffer.array(),0,byteBuffer.position()));
                byteBuffer.clear();
            }
            if (selectionKey.isWritable()) {
                ServerSocketChannel serverSocketChannel = (ServerSocketChannel) selectionKey.channel();
                SocketChannel socketChannel = serverSocketChannel.accept();
                byteBuffer.put("接受成功".getBytes());
                int readLength = socketChannel.write(byteBuffer);
            }
        }
    }

}
