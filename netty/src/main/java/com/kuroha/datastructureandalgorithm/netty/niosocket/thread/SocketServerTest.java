package com.kuroha.datastructureandalgorithm.netty.niosocket.thread;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author kuroha
 */
public class SocketServerTest {

    private static AtomicLong num = new AtomicLong();

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(2, 10, 1, TimeUnit.HOURS, new LinkedBlockingQueue<>(10), runnable -> new Thread(runnable,"com.kuroha.study.niosocket.thread.SocketServerTest-" + num.getAndIncrement()));

    public static void main(String[] args) throws IOException {
//        InetSocketAddress inetAddress = new InetSocketAddress("127.0.0.1",8848);
//        System.out.println(inetAddress.getHostName());
//        System.out.println(inetAddress.getHostString());
        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("localhost",8848));
        while (true) {
            Socket socket = serverSocket.accept();
            SocketConnectThread socketConnectThread = new SocketConnectThread(socket);
            executor.execute(socketConnectThread);
        }

    }

}
