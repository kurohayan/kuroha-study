package com.kuroha.datastructureandalgirithm.study.niosocket.socket;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author kuroha
 */
public class Server2 {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8848);
        Socket socket = serverSocket.accept();
        InputStream is = socket.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        char[] charArray = new char[1024];
        int read = isr.read(charArray);
        long beginTime = System.currentTimeMillis();
        while (read != -1) {
            String newString = new String(charArray, 0, read);
            read = isr.read(charArray);
        }
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - beginTime);
        socket.close();
        serverSocket.close();
    }

}
