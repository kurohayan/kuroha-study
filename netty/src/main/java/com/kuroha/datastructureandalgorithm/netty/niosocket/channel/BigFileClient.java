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
public class BigFileClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.connect(new InetSocketAddress("localhost",8848));
        Selector selector = Selector.open();
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        boolean flag = true;
        while (flag) {
            System.out.println("begin selector");
            if (socketChannel.isOpen()) {
                selector.select();
                System.out.println("end selector");
                Set<SelectionKey> selectionKeySet = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeySet.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if (selectionKey.isConnectable()) {
                        while (!socketChannel.finishConnect()) {

                        }
                        socketChannel.register(selector,SelectionKey.OP_READ);
                    }
                    if (selectionKey.isReadable()) {
                        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(50000);
                        int readLength = socketChannel.read(byteBuffer);
                        byteBuffer.flip();
                        long count = 0;
                        while (readLength != -1) {
                            count = count + readLength;
                            System.out.println("count=" + count + " readLength=" + readLength);
                            byteBuffer.clear();
                            readLength = socketChannel.read(byteBuffer);
                        }
                        System.out.println("读取结束");
                        socketChannel.close();
                        return;
                    }
                }
            }
        }
    }

}
