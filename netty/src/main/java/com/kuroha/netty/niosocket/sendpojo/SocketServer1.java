package com.kuroha.netty.niosocket.sendpojo;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author kuroha
 */
public class SocketServer1 {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ServerSocket serverSocket = new ServerSocket(8848);
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        for (int i = 0; i < 5; i++) {
            UserInfo userInfo = (UserInfo) objectInputStream.readObject();
            System.out.println(userInfo);
            userInfo = new UserInfo(i,"用户名"+i,"密码"+i);
            objectOutputStream.writeObject(userInfo);
        }
        objectInputStream.close();
        objectOutputStream.close();
        inputStream.close();
        outputStream.close();
        socket.close();
        serverSocket.close();
    }

}
