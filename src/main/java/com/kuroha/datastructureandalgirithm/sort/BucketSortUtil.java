package com.kuroha.datastructureandalgirithm.sort;

import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 * 桶排序-特殊排列
 * 使用要求:对排序数组的要求高.需要其基本满足等分.否则其时间复杂度会变成O(nlogn)
 * 原理:将数组平分到个个桶中,桶之间有序.桶内进行快排后导致桶内有序,最终从桶中依次取值.得到最终有序的数组
 * @author kuroha
 */
public class BucketSortUtil {

    private static final int BUCKET_SIZE = 100;

    private static final int INTERVAL = 1000;

    public static void sort(int[] data) {
        List<List<Integer>> buckets = new ArrayList<>(BUCKET_SIZE);
        for (int i = 0; i < BUCKET_SIZE; i++) {
            buckets.add(new ArrayList<>(INTERVAL));
        }
        for (int value : data) {
            buckets.get(value/INTERVAL).add(value);
        }
        int num = 0;
        for (List<Integer> bucket : buckets) {
            if (bucket.size() == 0) {
                continue;
            }
            int[] ints = new int[bucket.size()];
            int i = 0;
            for (Integer value : bucket) {
                ints[i++] = value;
            }
            quick_sort(ints,0,ints.length-1);
            for (int value : ints) {
                data[num++] = value;
            }
        }
    }

    /**
     * 快排
     * @param data 数据
     * @param start
     * @param end
     */
    public static void quick_sort (int[] data,int start,int end) {
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
        int[] data = new int[] {4,2,6,3,1,3,6,3,5,7,83,44,53};
        sort(data);
        System.out.println(JSON.toJSONString(data));
    }

}
