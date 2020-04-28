package com.kuroha.datastructureandalgorithm.algorithm.sort;

import com.alibaba.fastjson.JSON;

/**
 * 插入排序
 * 原理:从未排序队列中逐步找到最大值.并将其值放入已排序的最后一位,未排序的队列向后移动
 * @author kuroha
 */
public class InsertSortUtil {

    public static void sort(int[] data){
        int size = data.length;
        for (int i = 1; i < size; i++) {
            int temp = data[i];
            int j = i;
            for (;j>0; j--) {
                if (temp > data[j-1]) {
                    data[j] = data[j-1];
                } else {
                    break;
                }
            }
            data[j] = temp;
        }
    }

    public static void main(String[] args) {
        int[] data = new int[] {4,2,6,3,1,3};
        sort(data);
        System.out.println(JSON.toJSONString(data));
    }

}
