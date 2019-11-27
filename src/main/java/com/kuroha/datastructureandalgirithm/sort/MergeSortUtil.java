package com.kuroha.datastructureandalgirithm.sort;

import com.alibaba.fastjson.JSON;

/**
 * 归并排序
 * 原理:将大的排序问题拆分成小的排序问题,并且答案交由前一位进行计算.最后再从已排序的2个数组中依次取出值来组成有序队列
 * @author kuroha
 */
public class MergeSortUtil {

    public static void sort(int[] data) {
        merge_sort(data,0,data.length-1);
    }

    public static void merge_sort(int[] data, int start, int end) {
        if (start >= end) {
            return;
        }
        int p = end/2;
        int[] sort1 = new int[p-start+1];
        int[] sort2 = new int[end-p];
        for (int i = start, j = 0; i <= p; i++, j++) {
            sort1[j] = data[i];
        }
        for (int i = p + 1, j = 0; i <= end; i++, j++) {
            sort2[j] = data[i];
        }
        merge_sort(sort1, start, p);
        merge_sort(sort2, 0, end-p-1);
        merge(data,sort1,sort2);
    }
    public static void merge(int[] data, int[] data1, int[] data2) {
        int i1 = 0;
        int i2 = 0;
        int i = 0;
        for (; i < data.length; i++) {
            if (i1 >= data1.length) {
                break;
            }
            if (i2 >= data2.length) {
                break;
            }
            if (data1[i1] >= data2[i2]) {
                data[i] = data1[i1++];
            } else {
                data[i] = data2[i2++];
            }
        }
        for (int j = i1; j < data1.length; j++) {
            data[i++] = data1[j];
        }
        for (int j = i2; j < data2.length; j++) {
            data[i++] = data2[j];
        }
    }

    public static void main(String[] args) {
        int[] data = new int[] {4,2,6,3,1,3,6,3,5,7,83,523,44,53};
        sort(data);
        System.out.println(JSON.toJSONString(data));
    }

}
