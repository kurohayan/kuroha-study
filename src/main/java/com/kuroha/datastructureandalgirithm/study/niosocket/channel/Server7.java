package com.kuroha.datastructureandalgirithm.study.niosocket.channel;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.spi.AbstractSelectionKey;
import java.util.Iterator;
import java.util.Set;

/**
 * @author kuroha
 */
public class Server7 {

    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress(8848));
        serverSocketChannel.configureBlocking(false);
        ServerSocketChannel serverSocketChannel2 = ServerSocketChannel.open();
        serverSocketChannel2.bind(new InetSocketAddress(8888));
        serverSocketChannel2.configureBlocking(false);
        Selector selector = Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        serverSocketChannel2.register(selector, SelectionKey.OP_ACCEPT);
        Thread client = new Thread(() -> {
            try {
                Socket socket = new Socket("localhost",8848);
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write("客户端 to 8848".getBytes());
                socket.close();
                Socket socket2 = new Socket("localhost",8888);
                OutputStream outputStream2 = socket2.getOutputStream();
                outputStream2.write("客户端 to 8888".getBytes());
                socket2.close();
            }catch (Exception e) {
                e.printStackTrace();
            }
        });
        client.start();
        Thread getInfo = new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println();
                Set<SelectionKey> keys = selector.keys();
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                System.out.println("select() 方法执行第二次后的信息:");
                System.out.println(keys.size());
                System.out.println(selectionKeys.size());
            }catch (Exception e) {
                e.printStackTrace();
            }
        });
        getInfo.start();
        Thread.sleep(1000);
        boolean flag = true;
        while (flag) {
            int keyCount = selector.select();
            Set<SelectionKey> keys = selector.keys();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("取消之前的信息:");
            System.out.println(keys.size());
            System.out.println(selectionKeys.size());
            System.out.println();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                    ServerSocket serverSocket = channel.socket();
                    Socket socket = serverSocket.accept();
                    InputStream inputStream = socket.getInputStream();
                    byte[] bytes = new byte[1000];
                    int readLength = inputStream.read(bytes);
                    while (readLength != -1) {
                        String string = new String(bytes, 0, readLength);
                        System.out.println(string);
                        readLength = inputStream.read(bytes);
                    }
                    inputStream.close();
                    socket.close();
                    if (serverSocket.getLocalPort() == 8848) {
                        selectionKey.cancel();
                        System.out.println(keys.size());
                        System.out.println(selectionKeys.size());
                    }
                }
            }
        }
        serverSocketChannel.close();
        serverSocketChannel2.close();

    }

}
