package com.kuroha.algorithm.socket.server;

import com.kuroha.algorithm.socket.entry.pojo.BeginThread;
import com.kuroha.algorithm.socket.entry.pojo.NameThreadFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SocketServerTest {

    private static ThreadPoolExecutor executor = new ThreadPoolExecutor(4,4,1, TimeUnit.HOURS,new LinkedBlockingQueue<>(100),new NameThreadFactory());

    public static void main(String[] args) throws Exception {
        test4();
    }

    private static void test4() throws Exception {
        ServerSocket serverSocket = new ServerSocket(8848);
        System.out.println(1);
        int runTag = 1;
        while (runTag == 1) {
            Socket socket = serverSocket.accept();
            BeginThread beginThread = new BeginThread(socket);
            executor.execute(beginThread);
        }
        serverSocket.close();
    }
    private static void test3() throws Exception {
        ServerSocket serverSocket = new ServerSocket(8848);
        System.out.println(1);
        Socket socket = serverSocket.accept();
        System.out.println(2);
        Thread.sleep(Integer.MAX_VALUE);
        socket.close();
    }
    private static void test2() throws Exception {
        byte[] bs = new byte[2048];
        ServerSocket serverSocket = new ServerSocket(8848);
        Socket socket = serverSocket.accept();
        InputStream inputStream = socket.getInputStream();
        int readLength = inputStream.read(bs);
        FileOutputStream fileOutputStream = new FileOutputStream("/Users/a123123/sql/font/1.jpg");
        while (readLength != -1) {
            fileOutputStream.write(bs,0,readLength);
            readLength = inputStream.read(bs);
        }
        fileOutputStream.close();
        inputStream.close();
        serverSocket.close();
    }
    private static void test1() throws Exception {
        String getString;
        ServerSocket serverSocket = new ServerSocket(8848);
        Socket socket = serverSocket.accept();
        InputStream is = socket.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        while (!"".equals(getString = br.readLine())) {
            System.out.println(getString);
        }
        OutputStream os = socket.getOutputStream();
        os.write("HTTP/1.1 200 OK\r\n\r\n".getBytes());
        os.write("<html><a href='https://www.baidu.com'>www.baidu.com</a></html>".getBytes());
        os.flush();
        br.close();
        socket.close();
        serverSocket.close();
    }
}
