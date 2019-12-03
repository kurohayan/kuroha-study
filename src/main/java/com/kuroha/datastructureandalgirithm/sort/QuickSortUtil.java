package com.kuroha.datastructureandalgirithm.sort;

import com.alibaba.fastjson.JSON;

/**
 * 快速排序
 * 原理:取任意一个值作为中间值,将其拆分成大于其值的队列和小于其值的队列.再对这2个对面进行排序.只拆分.不传递.
 * @author kuroha
 */
public class QuickSortUtil {

    public static void sort(int[] data) {
        if (data.length == 0 || data.length == 1) {
            return;
        }
        quick_sort(data,0,data.length-1);
    }

    public static void quick_sort(int[] data,int start,int end) {
        if (start >= end) {
            return;
        }
        int size = end-start + 1;
        int p = data[end];
        int j = 0;
        int k = 0;
        int[] data1 = new int[size];
        int[] data2 = new int[size];
        for (int i = start; i < end; i++) {
            if (data[i] >= p) {
                data2[k++] = data[i];
            }else {
                data1[j++] = data[i];
            }
        }
        quick_sort(data1, 0, j - 1);
        quick_sort(data2, 0, k - 1);
        int i = start;
        for (int l = 0; l < k; l++) {
            data[i++] = data2[l];
        }
        data[i++] = p;
        for (int l = 0; l < j; l++) {
            data[i++] = data1[l];
        }
    }

    public static void main(String[] args) {
        int[] data = new int[] {4,2,6,3,1,3,6,3,5,7,83,523,44,53};
        sort(data);
        System.out.println(JSON.toJSONString(data));
    }

}
