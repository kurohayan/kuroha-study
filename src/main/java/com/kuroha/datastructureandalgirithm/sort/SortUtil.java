package com.kuroha.datastructureandalgirithm.sort;

/**
 * 综合排序算法
 * @author kuroha
 */
public class SortUtil {

    private static final int INSERT_SORT_MAX = 47;

    private static final int QUICK_SORT_MAX = 286;

    public static void sort(int[] data) {
        // 根据排序长度选择排序算法
        if (data.length < INSERT_SORT_MAX) {
            //小于INSERT_SORT_MAX则进行插入排序
            insertSort(data);
            return;
        }else if (data.length < QUICK_SORT_MAX) {
            //小于QUICK_SORT_MAX 使用快速排序
            quickSort(data);
            return;
        }
        //检测有序性
    }

    public static void insertSort(int[] data) {
        int size = data.length;
        for (int i = 1; i < size; i++) {
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

    public static void quickSort(int data[]) {

    }

}
