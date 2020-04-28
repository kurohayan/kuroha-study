package com.kuroha.datastructureandalgorithm.algorithm.socket.entry.pojo;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

/**
 * @author kuroha
 */
public class BeginThread implements Runnable {

    private Socket socket;

    public BeginThread(Socket socket) {
        super();
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ArrayList<Integer> list = new ArrayList<>();
            list.add(1);
            InputStream inputStream = socket.getInputStream();
            InputStreamReader reader = new InputStreamReader(inputStream);
            char[] chars = new char[1000];
            int readLength = -1;
            while ((readLength = reader.read(chars)) != -1) {
                String newString = new String(chars,0,readLength);
                System.out.println(newString);
            }
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
