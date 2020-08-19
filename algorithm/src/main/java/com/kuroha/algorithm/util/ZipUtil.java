package com.kuroha.algorithm.util;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author kuroha
 */
public class ZipUtil {
    public static void writAndTarFile(InputStream is, String path){
        writeFile(is, path);
        unZip(path);
    }
    public static void unZip(String path){
        String outPutDir = path.substring(0,path.lastIndexOf("/"));
        ZipFile zipFile = null;
        try {
            Charset charset = Charset.forName("CP866");
            zipFile = new ZipFile(new File(path),charset);
            createDirectory(outPutDir, null);
            Enumeration<? extends ZipEntry> enums = zipFile.entries();
            while (enums.hasMoreElements()){
                ZipEntry entry = enums.nextElement();
                // 是否是目录
                if (entry.isDirectory()) {
                    // 创建空目录
                    createDirectory(outPutDir,entry.getName());
                }else {
                    File tempFile = new File(outPutDir + "/" + entry.getName());
                    createDirectory(tempFile.getParent() + "/", null);
                    InputStream in = null;
                    OutputStream os = null;
                    try {
                        in = zipFile.getInputStream(entry);
                        os = new FileOutputStream(tempFile);
                        int len;
                        byte[] bs = new byte[2048];
                        while ((len = in.read(bs)) != -1){
                            os.write(bs,0,len);
                        }
                    } catch (Exception e){
                        e.printStackTrace();
                    } finally {
                        if (os != null){
                            os.close();
                        }
                    }
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipFile != null) {
                try {
                    zipFile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void writeFile(InputStream is, String path){
        FileOutputStream os = null;
        try {
            File file = new File(path.substring(0, path.lastIndexOf("/")));
            if (!file.exists()) {
                file.mkdirs();
            }
            os = new FileOutputStream(path);
            int len;
            byte[] bs = new byte[1024];
            while ((len = is.read(bs)) > 0){
                os.write(bs,0,len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 构建目录
     * @param outputDir
     * @param subDir
     */
    public static void createDirectory(String outputDir,String subDir){
        File file = new File(outputDir);
        /**
         * 子目录不为空
         */
        if(!(subDir == null || subDir.trim().equals(""))){
            file = new File(outputDir + "/" + subDir);
        }
        if(!file.exists()){
            file.mkdirs();
        }
    }
}
