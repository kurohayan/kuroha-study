package com.kuroha.datastructureandalgorithm.algorithm.search;

/**
 * 假设按照升序排序的数组在预先未知的某个点上进行了旋转。
 *
 * ( 例如，数组 [0,1,2,4,5,6,7] 可能变为 [4,5,6,7,0,1,2] )。
 *
 * 搜索一个给定的目标值，如果数组中存在这个目标值，则返回它的索引，否则返回 -1 。
 *
 * 来源：力扣（LeetCode）
 * 链接：https://leetcode-cn.com/problems/search-in-rotated-sorted-array
 * 著作权归领扣网络所有。商业转载请联系官方授权，非商业转载请注明出处。
 * @author kuroha
 */
public class BinarySearch6 {

    public static int search(int[] data, int start, int end, int value) {
        if (data[start] > data[end]) {
            int num = search(data, start, (end + start) >> 1, value);
            if (num != -1) {
                return num;
            }
            num = search(data, ((end + start) >> 1) + 1, end, value);
            return num;
        }
        while (start <= end) {
            int mid = start + ((end - start) >> 2);
            if (data[mid] > value) {
                end = mid - 1;
            } else if (data[mid] < value){
                start = mid + 1;
            } else {
                return mid;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        int[] data = new int[]{4,5,6,7,0,1,2};
        System.out.println(search(data,0,data.length-1,2));
    }

}
