package com.kuroha.netty.niosocket.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * @author kuroha
 */
public class Server4 {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8848));
        serverSocketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        boolean flag = true;
        while(flag) {
            int keyCount = selector.select();
            Set<SelectionKey> set1 = selector.keys();
            Set<SelectionKey> set2 = selector.selectedKeys();
            System.out.println("keyCount= " + keyCount);
            System.out.println("set1= " + set1.size());
            System.out.println("set2= " + set2.size());
            System.out.println();
            for (SelectionKey selectionKey : set2) {
                ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel)selectionKey.channel();
                SocketChannel socketChannel = serverSocketChannel.accept();
                System.out.println(serverSocketChannel);
                System.out.println(serverSocketChannel1);
                socketChannel.close();
            }
        }
        serverSocketChannel.close();
    }

}
