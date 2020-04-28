package com.kuroha.algorithm.find;

/**
 * 二分查找
 * @author kuroha
 */
public class BinarySearch {

    public static int find(int[] data,int num) {
        int low = 0;
        int high = data.length;
        int mid;
        while (true) {
            if (low > high) {
                return -1;
            }
            mid = low + ((high-low)>>1);
            if (data[mid] > num) {
                high = mid - 1;
            } else if (data[mid] < num) {
                low = mid + 1;
            } else {
                return mid;
            }
        }
    }

    public static void main(String[] args) {
        int[] data = new int[] {1,4,5,7,24,35,66,475};
        System.out.println(find(data,8));
    }

}
