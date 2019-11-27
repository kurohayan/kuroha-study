package com.kuroha.datastructureandalgirithm.sort;

import com.alibaba.fastjson.JSON;

/**
 * 选择排序
 * 原理:取未排序的第一个值,比较已排序对面,如果小于其值,插入到其后,后面的对面整体后移
 * @author kuroha
 */
public class SelectSortUtil {

    public static void sort(int[] data) {
        int size = data.length;
        for (int i = 0; i < size; i++) {
            int maxNum = i;
            for (int j = i + 1; j < size; j++) {
                if (data[j] > data[maxNum]) {
                    maxNum = j;
                }
            }
            int max = data[maxNum];
            for (int j = maxNum; j >i; j--) {
                data[j] = data[j-1];
            }
            data[i] = max;
        }
    }

    public static void main(String[] args) {
        int[] data = new int[] {4,2,6,3,1,3};
        sort(data);
        System.out.println(JSON.toJSONString(data));
    }

}
