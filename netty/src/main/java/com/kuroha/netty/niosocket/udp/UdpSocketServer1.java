package com.kuroha.netty.niosocket.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @author kuroha
 */
public class UdpSocketServer1 {

    public static void main(String[] args) throws Exception {
        DatagramSocket datagramSocket = new DatagramSocket(8848);
        DatagramPacket datagramPacket = new DatagramPacket(new byte[1024],1024);
        datagramSocket.receive(datagramPacket);
        datagramSocket.close();
        System.out.println(new String(datagramPacket.getData(),0,datagramPacket.getLength()));
    }

}
