package com.kuroha.netty.niosocket.channel;

import java.io.IOException;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.spi.SelectorProvider;

/**
 * @author kuroha
 */
public class Server3 {

    public static void main(String[] args) throws IOException {
        SelectorProvider provider = SelectorProvider.provider();
        System.out.println(provider);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        SelectorProvider provider1 = serverSocketChannel.provider();
        System.out.println(provider1);
        serverSocketChannel.close();
    }

}
