package com.kuroha.opencv.util;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/**
 * 哈希相似度算法（Hash algorithm）
 * 用一个快速算法，就达到基本的效果。哈希算法（Hash algorithm），它的作用是对每张图片生成一个固定位数的Hash 值（指纹 fingerprint）字符串，然后比较不同图片的指纹，结果越接近，就说明图片越相似。一般有如下三种生成Hash 值方法：
 * 差值DHash
 *
 * 缩小尺寸：将图片缩小到8x9的尺寸，总共72个像素。这一步的作用是去除图片的细节，只保留结构、明暗等基本信息，摒弃不同尺寸、比例带来的图片差异。
 * 简化色彩：将缩小后的图片，转为64级灰度（或者256级也行）。
 * 计算平均值：计算所有64个像素的灰度平均值。
 * 比较：同行相邻间对比，像素值大于后一个像素值记作1，相反记作0。每行9个像素，8个差值，有8行共64位
 * 计算哈希值：将上一步的比较结果，组合在一起，就构成了一个64位的整数，这就是这张图片的指纹。组合的次序并不重要，只要保证所有图片都采用同样次序就行了。
 *
 * @author samtofor
 */
@Slf4j
public class ImageDHashUtil {

    public static long calculateHash(byte[] image) {
        try {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(image));
            return calculateDHash(bufferedImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private static long calculateDHash(BufferedImage image) {
        long hash = 0;

        BufferedImage processedImage = getProcessedImage(image);

        for(int i = 1; i < 9; i++){
            for(int j = 1; j < 9; j++){
                int currBrightness = calculateBrightness(processedImage.getRGB(i, j));
                int prevBrightness = calculateBrightness(processedImage.getRGB(i - 1, j - 1));
                if(currBrightness > prevBrightness){
                    hash |= 1;
                }
                hash <<= 1;
            }
        }
        return hash;
    }

    private static BufferedImage getProcessedImage(BufferedImage image) {
        int width = 9, height = 9;
        BufferedImage colorless = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);
        Image scaled = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);

        colorless.getGraphics().drawImage(scaled, 0, 0, null);

        return colorless;
    }

    private static int calculateBrightness(int rgb) {
        return rgb & 0xff;
    }

}
