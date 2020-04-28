package com.kuroha.datastructureandalgorithm.algorithm.sort;

import com.alibaba.fastjson.JSON;

/**
 * 冒泡排序
 * 基础排序,前后比较,逐步将最小值调换到未排序的最后一位
 * @author kuroha
 */
public class BubbleSortUtil {

    public static void sort(int[] data) {
        int size = data.length;
        for (int i = 0; i < size; i++) {
            for (int j = 1; j < size - i; j++) {
                if (data[j] > data[j-1]) {
                    int temp = data[j-1];
                    data[j-1] = data[j];
                    data[j] = temp;
                }
            }
        }

    }

    public static void main(String[] args) {
        int[] data = new int[] {4,2,6,3,1,3};
        sort(data);
        System.out.println(JSON.toJSONString(data));
    }


}
