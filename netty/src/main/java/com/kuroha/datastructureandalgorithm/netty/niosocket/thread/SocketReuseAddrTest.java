package com.kuroha.datastructureandalgorithm.netty.niosocket.thread;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author kuroha
 */
public class SocketReuseAddrTest {

    public static void main(String[] args) throws InterruptedException {
        Thread server = new Thread(() -> {
            ServerSocket serverSocket = null;
            try {
                serverSocket = new ServerSocket();
                serverSocket.bind(new InetSocketAddress("localhost",8848));
                Socket socket = serverSocket.accept();
                Thread.sleep(1000);
                socket.close();
                serverSocket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        server.start();
        Thread.sleep(500);
        Thread client = new Thread(() -> {
            try {
                Socket socket = new Socket("localhost",8848);
                Thread.sleep(3000);
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        client.start();
    }

}
