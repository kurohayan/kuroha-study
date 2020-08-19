package com.kuroha.algorithm.util;

import java.io.*;
import java.util.Base64;

public class Base64Util {
    /**
     * 对byte[]类型数据进行Base64编码
     *
     * @param data
     * @return
     */
    public static String encode(byte[] data) {
        if (data == null) {
            return null;
        }
        return Base64.getEncoder().encodeToString(data);
    }
    /**
     * 对byte[]类型数据进行Base64编码
     *
     * @param data
     * @return
     */
    public static String decode(byte[] data) {
        if (data == null) {
            return null;
        }
        return new String(Base64.getDecoder().decode(data));
    }

    /**
     * 对String类型数据进行Base64解码
     *
     * @param data
     * @return
     */
    public static byte[] decodeToByte(String data) {
        if (data == null) {
            return null;
        }
        try {
            return Base64.getDecoder().decode(data);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 把字符串Base64解码为输入流
     *
     * @return InputStream
     * @author SunCheng 2017年6月6日 上午11:27:38
     */
    public static InputStream base64Decode(String imgStr) {
        try {
            // Base64解码
            byte[] b = Base64.getDecoder().decode(imgStr);
            for (int i = 0; i < b.length; ++i) {
                // 调整异常数据
                if (b[i] < 0) {
                    b[i] += 256;
                }
            }
            InputStream input = new ByteArrayInputStream(b);

            return input;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String base64Encode(String str) {
        try {
            return Base64.getEncoder().encodeToString(str.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] streamToBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = is.read(buff, 0, 100)) > 0) {
            byteArrayOutputStream.write(buff, 0, rc);
        }
        return byteArrayOutputStream.toByteArray();
    }

    public static String encode(InputStream is) throws IOException {
        byte[] data = streamToBytes(is);
        if (data == null)
            return null;
        return Base64.getEncoder().encodeToString(data);
    }
}
