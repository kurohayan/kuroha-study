package com.kuroha.algorithm.util;

import java.util.Random;

/**
 * @author kuroha
 */
public class MathUtil {

    private static final Random RANDOM = new Random();

    public static int getNonce() {
        return (RANDOM.nextInt(900000) + 100000);
    }

    /**
     * 获取6位随机数
     * @return
     */
    public static int random6() {
        Random random = new Random();
        return random.nextInt(900000) + 100000;
    }

    /**
     * 获取指定指定长度随机数
     * @param maxSize
     * @return
     */
    public static int random(int maxSize) {
        Random random = new Random();
        int maxNum = 10;
        for (int i = 1; i < maxSize; i++) {
            maxNum *=10;
        }
        int seed = maxNum/10;
        maxNum -= seed;
        return random.nextInt(maxNum) + seed;
    }
}
