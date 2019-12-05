package com.kuroha.datastructureandalgirithm.sort;

import com.alibaba.fastjson.JSON;
import org.junit.Test;

import java.util.Random;

/**
 * 排序算法对比
 * @author kuroha
 */
public class SortTest {

    @Test
    public void test() {
        Random random = new Random();
        int[] data = new int[100000];
        for (int i = 0; i < 100000; i++) {
            data[i] = random.nextInt(100000);
        }
        long startTime = 0;
        long endTime = 0;
//        int[] data1 = data.clone();
//        startTime = System.currentTimeMillis();
//        BubbleSortUtil.sort(data1);
//        endTime = System.currentTimeMillis();
//        System.out.println("冒泡排序时间:" + (endTime - startTime));
        int[] data2 = data.clone();
        startTime = System.currentTimeMillis();
        InsertSortUtil.sort(data2);
        endTime = System.currentTimeMillis();
        System.out.println("插入排序时间:" + (endTime - startTime));
//        int[] data3 = data.clone();
//        startTime = System.currentTimeMillis();
//        SelectSortUtil.sort(data3);
//        endTime = System.currentTimeMillis();
//        System.out.println("选择排序时间:" + (endTime - startTime));
        int[] data4 = data.clone();
        startTime = System.currentTimeMillis();
        MergeSortUtil.sort(data4);
        endTime = System.currentTimeMillis();
        System.out.println("并归排序时间:" + (endTime - startTime));
        int[] data5 = data.clone();
        startTime = System.currentTimeMillis();
        QuickSortUtil.sort(data5);
        endTime = System.currentTimeMillis();
        System.out.println("快速排序时间:" + (endTime - startTime));
        int[] data6 = data.clone();
        startTime = System.currentTimeMillis();
        BucketSortUtil.sort(data6);
        endTime = System.currentTimeMillis();
        System.out.println("桶排序时间:" + (endTime - startTime));
        int[] data7 = data.clone();
        startTime = System.currentTimeMillis();
        SortUtil.sort(data7);
        endTime = System.currentTimeMillis();
        System.out.println("综合排序:" + (endTime - startTime));

    }

}
