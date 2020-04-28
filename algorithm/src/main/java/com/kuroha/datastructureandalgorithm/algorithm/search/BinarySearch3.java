package com.kuroha.datastructureandalgorithm.algorithm.search;

/**
 * 查找最后一个值等于给定值的元素
 * @author kuroha
 */
public class BinarySearch3 {

    public static int search(int[] data, int num) {
        int low = 0;
        int high = data.length - 1;
        while (low <= high) {
            int mid = low + ((high-low)>>1);
            if (data[mid] <= num) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        if (high >= 0 && data[high] == num) {
            return high;
        } else {
            return -1;
        }
    }
    public static int search2(int[] data, int num) {
        int low = 0;
        int size = data.length - 1;
        int high = size;
        while (low <= high) {
            int mid = low + ((high-low)>>1);
            if (data[mid] > num) {
                high = mid - 1;
            } else if (data[mid] < num) {
                low = mid + 1;
            } else {
                if (mid == size || data[mid + 1] != num) {
                    return mid;
                } else {
                    low = mid + 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] data = new int[]{1,2,3,4,5,6,7,8,9,10,10,10,11,12,13};
        System.out.println(search(data,10));
    }

}
