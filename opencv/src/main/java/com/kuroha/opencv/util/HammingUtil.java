package com.kuroha.opencv.util;

/**
 * @author samtofor
 */
public class HammingUtil {
    /**
     *  汉明距离越大表明图片差异越大，如果不相同的数据位不超过5，就说明两张图片很相似；如果大于10，就说明这是两张不同的图片。
     * @param str1
     * @param str2
     * @return
     */
    public static int distance(String str1, String str2) {
        char[] chars1 = str1.toCharArray();
        char[] chars2 = str2.toCharArray();
        int difference = 0;
        //遍历字符串比较两个字符串的0与1的不相同的地方，不相同一次就长度增加1从而计
        //算总距离
        for (int i = 0; i < 64; i++) {
            if (chars1[i] != chars2[i]) {
                difference++;
            }
        }
        return difference;
    }
}
