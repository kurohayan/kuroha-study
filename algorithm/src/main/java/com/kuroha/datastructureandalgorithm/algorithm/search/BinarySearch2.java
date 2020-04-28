package com.kuroha.datastructureandalgorithm.algorithm.search;

/**
 * 查找第一个值等于给定值的元素
 * @author kuroha
 */
public class BinarySearch2 {

    public static int search(int[] data, int num) {
        int low = 0;
        int high = data.length - 1;
        while (low <= high) {
            int mid = low + ((high-low)>>1);
            if (data[mid] >= num) {
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        if(low < data.length && data[low] == num) {
            return low;
        } else {
            return -1;
        }
    }
    public static int search2(int[] data, int num) {
        int low = 0;
        int high = data.length - 1;
        while (low <= high) {
            int mid = low + ((high-low)>>1);
            if (data[mid] > num) {
                high = mid - 1;
            } else if (data[mid] < num){
                low = mid + 1;
            } else {
                if (mid == 0 || data[mid -1] != num) {
                    return mid;
                } else {
                    high = mid - 1;
                }
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] data = new int[]{1,2,3,4,5,6,7,8,9,10,10,10,11,12,13};
        System.out.println(search2(data,10));
    }

}
