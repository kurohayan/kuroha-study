package com.kuroha.datastructureandalgirithm.sort;

import com.alibaba.fastjson.JSON;

import java.util.*;

/**
 * 桶排序
 * @author kuroha
 */
public class BucketSortUtil {

    private static final int MAP_SIZE = 10;

    private static final int INTERVAL = 10;

    public static void sort(int[] data) {
        int size = data.length;
        Map<Integer, int[]> map = new TreeMap<>((o1, o2) -> o2-o1);
        Map<Integer, Integer> sizeMap = new HashMap<>((int)(MAP_SIZE * 1.5));
        for (int i = 0; i < MAP_SIZE; i++) {
            int[] ints = new int[size];
            map.put(i,ints);
            sizeMap.put(i,0);
        }
        for (int num : data) {
            int i = num / INTERVAL;
            Integer value = sizeMap.get(i);
            map.get(i)[value] = num;
            sizeMap.put(i,++value);
        }
        int num = 0;
        for (int i = MAP_SIZE-1; i >=0; i--) {
            int[] value = map.get(i);
            QuickSortUtil.sort(value);
            int mapSize = sizeMap.get(i);
            for (int j = 0; j < mapSize; j++) {
                data[num++] = value[j];
            }
        }
    }

    public static void quick_sort (int[] data,int start,int end) {
        if (start >= end) {
            return;
        }
        int size = end - start + 1;
        int p = data[end-1];
        int j = 0;
        int k = 0;
        int[] data1 = new int[size];
        int[] data2 = new int[size];
        for (int i = 0; i < size - 1; i++) {
            if (data[i] >= p) {
                data2[k++] = data[i];
            } else {
                data1[j++] = data[i];
            }
        }
        quick_sort(data1,0,j-1);
        quick_sort(data2,0,k-1);
        int i = 0;
        for (int l = 0; l < k; l++) {
            data[i++] = data2[l];
        }
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
