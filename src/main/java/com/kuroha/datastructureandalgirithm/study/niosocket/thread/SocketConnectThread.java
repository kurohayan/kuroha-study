package com.kuroha.datastructureandalgirithm.study.niosocket.thread;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * @author kuroha
 */
public class SocketConnectThread implements Runnable {

    private Socket socket;

    public SocketConnectThread(Socket socket) {
        super();
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            InputStream inputStream = socket.getInputStream();
            byte[] bs = new byte[8192];
            int read = inputStream.read(bs);
            System.out.println(new String(bs,0,read));
            inputStream.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
