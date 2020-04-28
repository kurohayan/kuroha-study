package com.kuroha.datastructureandalgorithm.algorithm.socket;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * @author kuroha
 */
public class SocketTest1 {

    public static void main(String[] args) throws Exception {
        test2();
    }

    private static void test2() throws Exception {
        InetAddress baidu = InetAddress.getByName("www.baidu.com");
        InetAddress ip = InetAddress.getByName("192.168.4.246");
        InetAddress localhost = InetAddress.getByName("localhost");
        InetAddress google = InetAddress.getByName("www.google.com");
        System.out.println(baidu.getHostAddress());
        System.out.println(ip.getHostAddress());
        System.out.println(localhost.getHostAddress());
        System.out.println(google.getHostAddress());
    }

    private static void test1() throws Exception {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();
            System.out.println(networkInterface.getName());
            System.out.println(networkInterface.getDisplayName());
            System.out.println(networkInterface.getIndex());
            Enumeration<InetAddress> enumeration = networkInterface.getInetAddresses();
            while (enumeration.hasMoreElements()) {
                InetAddress inetAddress = enumeration.nextElement();
                System.out.println("主机名:" + inetAddress.getHostName());
                System.out.println("ip地址:" + inetAddress.getHostAddress());
                System.out.print("原始ip:");
                byte[] bs = inetAddress.getAddress();
                for (byte b : bs) {
                    System.out.print(b + " ");
                }
                System.out.println("");
            }
//            List<InterfaceAddress> interfaceAddresses = networkInterface.getInterfaceAddresses();
//            interfaceAddresses.forEach(interfaceAddress -> System.out.println("--" + interfaceAddress.getAddress()));
            System.out.println("-------------------");
        }
    }
}
