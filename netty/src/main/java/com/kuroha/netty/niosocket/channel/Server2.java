package com.kuroha.netty.niosocket.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketOption;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author kuroha
 */
public class Server2 {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("localhost",8848));
        SocketChannel socketChannel = serverSocketChannel.accept();
        Set<SocketOption<?>> set1 = serverSocketChannel.supportedOptions();
        Set<SocketOption<?>> set2 = socketChannel.supportedOptions();
        Iterator<SocketOption<?>> iterator1 = set1.iterator();
        Iterator<SocketOption<?>> iterator2 = set2.iterator();
        while (iterator1.hasNext()) {
            SocketOption<?> socketOption = iterator1.next();
            System.out.println(socketOption.name() + " " + socketOption.getClass().getName());
        }
        System.out.println(" ");
        while (iterator2.hasNext()) {
            SocketOption<?> socketOption = iterator2.next();
            System.out.println(socketOption.name() + " " + socketOption.getClass().getName());
        }
        socketChannel.close();
        serverSocketChannel.close();
    }

}
