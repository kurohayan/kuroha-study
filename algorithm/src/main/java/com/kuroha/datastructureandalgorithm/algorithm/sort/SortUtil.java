package com.kuroha.datastructureandalgorithm.algorithm.sort;

import com.alibaba.fastjson.JSON;

/**
 * 综合排序算法
 * @author kuroha
 */
public class SortUtil {

    private static final int INSERT_SORT_MAX = 48;

    private static final int QUICK_SORT_MAX = 286;

    public static void sort(int[] data) {
        // 根据排序长度选择排序算法
        if (data.length < INSERT_SORT_MAX) {
            //小于INSERT_SORT_MAX则进行插入排序
            insertSort(data,0,data.length);
            return;
        }else if (data.length < QUICK_SORT_MAX) {
            //小于QUICK_SORT_MAX 使用快速排序
            quickSort(data,0,data.length-1);
            return;
        } else {
            mergeSort(data);
        }
        //检测有序性
    }

    /**
     * 插入排序
     * @param data
     * @param start
     * @param end
     */
    public static void insertSort(int[] data,int start,int end) {
        for (int i = start + 1; i < end; i++) {
            int temp = data[i];
            int j = i;
            for (;j>0; j--) {
                if (data[j-1] > temp) {
                    data[j] = data[j-1];
                } else {
                    break;
                }
            }
            data[j] = temp;
        }
    }

    /**
     * 快速排序
     * @param data
     * @param start
     * @param end
     */
    public static void quickSort(int[] data,int start,int end) {
        int size = end - start + 1;
        if (size < 4) {
            insertSort(data,start,end);
            return;
        }
        int p = start + (end-start)>>1;
        int temp = data[p];
        int j = 0;
        int k = 0;
        int[] data1 = new int[size];
        int[] data2 = new int[size];
        for (int i = start; i <= end; i++) {
            if (i==p) {
                continue;
            }
            if (data[i] > temp) {
                data2[k++] = data[i];
            } else {
                data1[j++] = data[i];
            }
        }
        quickSort(data1,0,j - 1);
        quickSort(data2,0,k - 1);
        int dataNum = 0;
        for (int i = 0; i < j; i++) {
            data[dataNum++] = data1[i];
        }
        data[dataNum++] = temp;
        for (int i = 0; i < k; i++) {
            data[dataNum++] = data2[i];
        }
    }

    /**
     * 并归排序
     * @param data
     */
    private static void mergeSort(int[] data) {
        int length = data.length;
        for (int i = 0; i < length; i++) {

        }
    }

    public static void main(String[] args) {
        int[] data = new int[] {4,2,6,3,1,3,6,3,5,7,83,523,44,53};
        sort(data);
        System.out.println(JSON.toJSONString(data));
    }

}
