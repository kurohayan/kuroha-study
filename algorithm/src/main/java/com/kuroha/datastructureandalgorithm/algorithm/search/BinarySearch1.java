package com.kuroha.datastructureandalgorithm.algorithm.search;

/**
 * 二分查找
 * @author kuroha
 */
public class BinarySearch1 {

    public static int search(int[] data, int num) {
        int low = 0;
        int high = data.length - 1;
        while (low <= high) {
            int mid = low + ((high-low)>>1);
            if (data[mid] > num) {
                high = mid - 1;
            } else if (data[mid] < num) {
                low = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] data = new int[]{1,2,3,4,5,6,7,8,9,10,11,12,13};
        System.out.println(search(data,13));
    }

}
