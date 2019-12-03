package com.kuroha.datastructureandalgirithm.sort;

import com.alibaba.fastjson.JSON;

/**
 * 计数排序
 * @author kuroha
 */
public class CountingSortUtil {

    public static void sort(int[] data) {
        int[] ints = new int[101];
        for (int i = 0; i < data.length; i++) {
            ints[data[i]] = ++ints[data[i]];
        }
        for (int i = 1; i < ints.length; i++) {
            ints[i] = ints[i-1] + ints[i];
        }
        int num = 0;
        for (int i = ints.length - 1; i > 0; i--) {
            int dd = ints[i];
            int dt = ints[i-1];
            for (int j = dt; j < dd; j++) {
                data[num++] = i;
            }
        }
        for (int i = 0; i < ints[0]; i++) {
            data[num++] = 0;
        }
    }

    public static void main(String[] args) {
        int[] data = new int[]{1,2,4,2,5,2,6,12,3,2,1,2,5,5,23,4,23};
        sort(data);
        System.out.println(JSON.toJSON(data));
    }

}
