package com.kuroha.datastructureandalgorithm.algorithm.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.StandardOpenOption;

/**
 * 比特选择器
 */
public class NioTest1 {

    public static void main(String[] args) throws Exception {
        FileChannel.open(new File("").toPath(), StandardOpenOption.CREATE);
        readPosition();
    }

    public static void readPosition() throws Exception {
        FileInputStream fis = new FileInputStream("/Users/a123123/sql/tt.txt");
        FileChannel channel = fis.getChannel();
        try {
            ByteBuffer buffer = ByteBuffer.allocate(2);
            channel.read(buffer,2);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            channel.close();
            fis.close();
        }
    }
    public static void arrayWrite() throws Exception {
        FileOutputStream fos = new FileOutputStream("/Users/a123123/sql/tt.txt");
        FileChannel channel = fos.getChannel();
        try {
            ByteBuffer buffer1 = ByteBuffer.wrap("abc".getBytes());
            ByteBuffer buffer2 = ByteBuffer.wrap("xyz".getBytes());
            ByteBuffer[] bs = new ByteBuffer[]{buffer1,buffer2};
            channel.write(ByteBuffer.wrap("8888".getBytes()));
            channel.position(1);
            channel.write(bs);
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            channel.close();
            fos.close();
        }
    }

    public static void read() throws Exception {
        FileInputStream fis = new FileInputStream("/Users/a123123/sql/tt.txt");
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);
        FileChannel channel = fis.getChannel();
        channel.position(1);
        channel.read(byteBuffer);
        byteBuffer.flip();
        while (byteBuffer.remaining()!=0) {
            System.out.print((char)byteBuffer.get());
        }
    }

    public static void write() throws Exception {
        FileOutputStream fos = new FileOutputStream("/Users/a123123/sql/tt.txt");
        FileChannel channel = fos.getChannel();
        try {
            ByteBuffer buffer1 = ByteBuffer.wrap("abcde".getBytes());
            ByteBuffer buffer2 = ByteBuffer.wrap("12345".getBytes());
            channel.write(buffer1);
            buffer2.position(1);
            buffer2.limit(3);
            channel.position(2);
            channel.write(buffer2);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            channel.close();
            fos.close();
        }
    }

}
