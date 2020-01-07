package com.kuroha.datastructureandalgirithm.study.niosocket.channel;

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
public class Server6 {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8848));
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        boolean flag = true;
        while (flag) {
            int selectCount = selector.select();
            Set<SelectionKey> set = selector.selectedKeys();
            for (SelectionKey key : set) {
                ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                SocketChannel socketChannel = channel.accept();
                socketChannel.configureBlocking(false);
                SelectionKey selectionKey = socketChannel.register(selector, SelectionKey.OP_READ);
                System.out.println(selectionKey);
            }
        }
    }

}
