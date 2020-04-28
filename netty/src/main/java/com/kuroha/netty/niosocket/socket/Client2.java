package com.kuroha.netty.niosocket.socket;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * @author kuroha
 */
public class Client2 {

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket();
        System.out.println(socket.getSendBufferSize());
        socket.setSendBufferSize(1024*1024);
        socket.setTrafficClass(0x10);
        System.out.println(socket.getSendBufferSize());
        socket.connect(new InetSocketAddress("localhost", 8848));
        OutputStream outputStream = socket.getOutputStream();
        for (int i = 0; i < 500000; i++) {
            outputStream.write("1".getBytes());
        }
        outputStream.write("end!".getBytes());
        outputStream.close();
        socket.close();
    }

}
