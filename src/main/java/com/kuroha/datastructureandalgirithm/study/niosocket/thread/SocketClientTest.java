package com.kuroha.datastructureandalgirithm.study.niosocket.thread;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

/**
 * @author kuroha
 */
public class SocketClientTest {

    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("localhost",8848);
        Thread.sleep(3000);
        socket.close();
    }

}
