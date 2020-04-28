package com.kuroha.datastructureandalgorithm.netty.niosocket.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author kuroha
 */
public class Client8 {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        Selector selector = Selector.open();
        SelectionKey selectionKey = socketChannel.register(selector,SelectionKey.OP_CONNECT);
        socketChannel.connect(new InetSocketAddress("localhost",8848));
        boolean run = true;
        while (run) {
            int select = selector.select();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isConnectable()) {
                    while (!socketChannel.finishConnect()) {
                        System.out.println("!socketChannel.finishConnect()-------");
                    }
                    socketChannel.register(selector,SelectionKey.OP_WRITE,"我来自客户端");

                }
                if (key.isWritable()) {
                    System.out.println("client is Writable()");
                    SocketChannel channel = (SocketChannel) key.channel();
                    channel.write(ByteBuffer.wrap(key.attachment().toString().getBytes()));
                    channel.close();
                }

            }
            System.out.println("client end !");
        }
    }

}
