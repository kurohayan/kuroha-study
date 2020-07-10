package com.kuroha.algorithm.socket.client;

import com.kuroha.algorithm.list.ArrayList;
import com.kuroha.algorithm.list.LinkedList;
import com.kuroha.algorithm.list.LruArrayList;
import com.kuroha.algorithm.list.LruLinkedList;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashMap;

public class SocketClientTest {

    public static void main(String[] args) throws Exception {
        ArrayList<Integer> list = new ArrayList<>(0);
        HashMap<String,String> map = new HashMap<>();
        list.add(1,2,51,2,5,5,2,3,5,6,3,5,2,5,6,23);
        System.out.println(list.get(3));
        long l = System.nanoTime();
        System.out.println(list);
        long l1 = System.nanoTime();
        System.out.println(l1-l);
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(1,2);
        linkedList.add(2,1);
        System.out.println(linkedList.palindrome());
        System.out.println(linkedList);
        LruLinkedList<Integer> lruLinkedList = new LruLinkedList<>(4);
        lruLinkedList.add(1);
        lruLinkedList.add(1,2,3,4,5,6,7);
        System.out.println(lruLinkedList);
        System.out.println(lruLinkedList.get(3));
        LruArrayList<Integer> lruArrayList = new LruArrayList<>(4);
        lruArrayList.add(1);
        lruArrayList.add(1,2,3,4);
        lruArrayList.add(2,3,1);
        System.out.println(lruArrayList);
        lruArrayList.add(5);
        System.out.println(lruArrayList);

    }

    private static void test4() throws Exception {
        for (int i = 0; i < 110; i++) {
            System.out.println(1);
            Socket socket = new Socket(InetAddress.getLocalHost(),8848);
            System.out.println(2);
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write("测试".getBytes());
            socket.close();
        }
    }
    private static void test3() throws Exception {
        System.out.println(1);
        Socket socket = new Socket(InetAddress.getLocalHost(),8848);
        System.out.println(2);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("111".getBytes());
        outputStream.write("111111".getBytes());
        outputStream.write("111111111".getBytes());
        Thread.sleep(5000);
        socket.close();
    }
    private static void test2() throws Exception {
        Socket socket = new Socket(InetAddress.getLocalHost(),8848);
        OutputStream outputStream = socket.getOutputStream();
        FileInputStream fis = new FileInputStream("/Users/a123123/Pictures/image1.jpg");
        byte[] bs = new byte[8192];
        int readLength = fis.read(bs);
        while (readLength != -1) {
            outputStream.write(bs,0,readLength);
            readLength = fis.read(bs);
        }
        fis.close();
        outputStream.close();
    }
    private static void test1() throws Exception {
        System.out.println("1");
        Socket socket = new Socket(InetAddress.getLocalHost(),8848);
        System.out.println("2");
        socket.close();
    }
}
