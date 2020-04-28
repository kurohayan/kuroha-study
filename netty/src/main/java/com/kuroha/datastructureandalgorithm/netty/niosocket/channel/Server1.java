package com.kuroha.datastructureandalgorithm.netty.niosocket.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * @author kuroha
 */
public class Server1 {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.bind(new InetSocketAddress("localhost",8848),20);
        channel.configureBlocking(false);
        ServerSocket serverSocket = channel.socket();
        Selector selector = Selector.open();
        SelectionKey selectionKey = channel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("selector:" + selector);
        System.out.println("key:" + selectionKey);
        SelectionKey selectionKey1 = channel.keyFor(selector);
        System.out.println("key1:" + selectionKey1);
        System.out.println(channel.isRegistered());
        serverSocket.close();
        channel.close();
    }

}
