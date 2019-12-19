package com.kuroha.datastructureandalgirithm.search;

/**
 * 查找第一个值等于给定值的元素
 * @author kuroha
 */
public class BinarySearch2 {

    public static int search(int[] data, int num) {
        int low = 0;
        int high = data.length - 1;
        int mid = 0;
        while (low <= high) {
            mid = low + ((high-low)>>1);
            if (data[mid] > num) {
                high = mid - 1;
            } else if (data[mid] < num) {
                low = mid + 1;
            } else {
                break;
            }
        }
        while (true) {
            if (data[mid] == data[mid-1]) {
                mid--;
            } else {
                return mid;
            }
        }
    }

    public static void main(String[] args) {
        int[] data = new int[]{1,2,3,4,5,6,7,8,9,10,10,10,11,12,13};
        System.out.println(search(data,10));
    }

}
