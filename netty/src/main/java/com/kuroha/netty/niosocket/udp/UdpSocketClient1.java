package com.kuroha.netty.niosocket.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * @author kuroha
 */
public class UdpSocketClient1 {

    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        socket.connect(new InetSocketAddress("localhost",8848));
        String str = "1234567890";
        DatagramPacket datagramPacket = new DatagramPacket(str.getBytes(),str.length());
        socket.send(datagramPacket);
        socket.close();
    }

}
