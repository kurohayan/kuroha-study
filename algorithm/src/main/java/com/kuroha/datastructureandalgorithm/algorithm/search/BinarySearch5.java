package com.kuroha.datastructureandalgorithm.algorithm.search;

/**
 * 查找最后一个小于等于给定值的元素
 * @author kuroha
 */
public class BinarySearch5 {

    public static int search(int[] data, int num) {
        int low = 0;
        int size = data.length - 1;
        int high = size;
        while (low <= high) {
            int mid = low + ((high-low)>>1);
            if (data[mid] > num) {
                high = mid - 1;
            } else {
                if (mid == size || data[mid + 1] > num) {
                    return mid;
                } else {
                    low = mid + 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] data = new int[]{1,2,3,4,5,6,7,9,10,11,12,13};
        System.out.println(search(data,8));
    }

}
