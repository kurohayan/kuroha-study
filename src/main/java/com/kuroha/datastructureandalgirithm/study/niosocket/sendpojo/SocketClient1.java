package com.kuroha.datastructureandalgirithm.study.niosocket.sendpojo;

import java.io.*;
import java.net.Socket;

/**
 * @author kuroha
 */
public class SocketClient1 {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Socket socket = new Socket("localhost",8848);
        socket.setTcpNoDelay(false);
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        for (int i = 0; i < 5; i++) {
            UserInfo userInfo = new UserInfo(i,"用户名"+i,"密码"+i);
            objectOutputStream.writeObject(userInfo);
            userInfo = (UserInfo) objectInputStream.readObject();
            System.out.println(userInfo);
        }
        objectInputStream.close();
        objectOutputStream.close();
        inputStream.close();
        outputStream.close();
        socket.close();
    }
}
