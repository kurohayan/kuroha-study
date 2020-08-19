package com.kuroha.algorithm.util;

/**
 * @author kuroha
 */
public class SortUtil {
	/**
	 * 快速排序
	 *
	 * @param arr
	 * @param start
	 * @param end
	 * @param arr2
	 */
	public static void quickSort(int[] arr, int start, int end, Object[] arr2) {
		int l = start;
		int h = end;
		int k = arr[start];
		while (l < h) {
			// 从后往前比较
			// 如果没有比关键值小的，比较下一个，直到有比关键值小的交换位置，然后又从前往后比较
			while (l < h && arr[h] <= k) {
				h--;// h=6
			}
			if (l < h) {
				swap(arr, arr2, l, h);
				//进行过一次替换后，没必要将替换后的两值再次比较，所以i++直接下一位与k对比
				l++;
			}
			// 从前往后比较
			// 如果没有比关键值大的，比较下一个，直到有比关键值大的交换位置
			while (l < h && arr[l] >= k) {
				l++;
			}
			if (l < h) {
				swap(arr, arr2, l, h);
				h--;
			}
			// 此时第一次循环比较结束，关键值的位置已经确定了。左边的值都比关键值小，右边的值都比关键值大，但是两边的顺序还有可能是不一样的，进行下面的递归调用
		}
		// 递归
		//先判断l>low再次经行左边排序
		if (l > start) {
			// 左边序列。第一个索引位置到关键值索引-1
			quickSort(arr, start, l - 1, arr2);
		}
		//左边依次排序执行完递归后，弹栈进行右边排序
		if (h < end) {
			// 右边序列。从关键值索引+1到最后一个
			quickSort(arr, l + 1, end, arr2);
		}

	}

	/**
	 * 快速排序
	 *
	 * @param arr
	 * @param start
	 * @param end
	 * @param arr2
	 */
	public static void quickSort(double[] arr, int start, int end, Object[] arr2) {
		int l = start;
		int h = end;
		double k = arr[start];
		while (l < h) {
			// 从后往前比较
			// 如果没有比关键值小的，比较下一个，直到有比关键值小的交换位置，然后又从前往后比较
			while (l < h && arr[h] <= k) {
				h--;// h=6
			}
			if (l < h) {
				swap(arr, arr2, l, h);
				//进行过一次替换后，没必要将替换后的两值再次比较，所以i++直接下一位与k对比
				l++;
			}
			// 从前往后比较
			// 如果没有比关键值大的，比较下一个，直到有比关键值大的交换位置
			while (l < h && arr[l] >= k) {
				l++;
			}
			if (l < h) {
				swap(arr, arr2, l, h);
				h--;
			}
			// 此时第一次循环比较结束，关键值的位置已经确定了。左边的值都比关键值小，右边的值都比关键值大，但是两边的顺序还有可能是不一样的，进行下面的递归调用
		}
		// 递归
		//先判断l>low再次经行左边排序
		if (l > start) {
			// 左边序列。第一个索引位置到关键值索引-1
			quickSort(arr, start, l - 1, arr2);
		}
		//左边依次排序执行完递归后，弹栈进行右边排序
		if (h < end) {
			// 右边序列。从关键值索引+1到最后一个
			quickSort(arr, l + 1, end, arr2);
		}

	}

	private static void swap(double[] arr, Object[] arr2, int l, int h) {
		double temp = arr[h];
		arr[h] = arr[l];
		arr[l] = temp;
		if (arr2 != null) {
			Object temp2 = arr2[h];
			arr2[h] = arr2[l];
			arr2[l] = temp2;
		}
	}

	private static void swap(int[] arr, Object[] arr2, int l, int h) {
		int temp = arr[h];
		arr[h] = arr[l];
		arr[l] = temp;
		if (arr2 != null) {
			Object temp2 = arr2[h];
			arr2[h] = arr2[l];
			arr2[l] = temp2;
		}
	}

	/**
	 * 堆排序(从小到大)
	 *
	 * @param arr
	 * @param arr2
	 */
	public static void maxHeapSort(int[] arr, Object[] arr2) {
		if (arr == null || arr.length == 0) {
			return;
		}
		//建立大顶堆
		for (int i = arr.length / 2; i >= 0; i--) {
			heapAdjust(arr, i, arr.length - 1, arr2);
		}
		for (int i = arr.length - 1; i >= 0; i--) {
			swap(arr, arr2, 0, i);
			heapAdjust(arr, 0, i - 1, arr2);
		}
	}

	/**
	 * 堆筛选，除了start之外，start~end均满足大顶堆的定义。
	 * 调整之后start~end称为一个大顶堆。
	 *
	 * @param arr   待调整数组
	 * @param start 起始指针
	 * @param end   结束指针
	 */
	private static void heapAdjust(int[] arr, int start, int end, Object[] arr2) {
		int temp = arr[start];
		Object temp2 = arr2[start];
		for (int i = 2 * start + 1; i <= end; i *= 2) {
			//左右孩子的节点分别为2*i+1,2*i+2
			//选择出左右孩子较小的下标
			if (i < end && arr[i] < arr[i + 1]) {
				i++;
			}
			if (temp >= arr[i]) {
				//已经为大顶堆，=保持稳定性。
				break;
			}
			//将子节点上移
			arr[start] = arr[i];
			arr2[start] = arr2[i];
			//下一轮筛选
			start = i;
		}
		//插入正确的位置
		arr[start] = temp;
		arr2[start] = temp2;
	}

	/**
	 * 堆排序(从小到大)
	 *
	 * @param arr
	 * @param arr2
	 */
	public static void maxHeapSort(double[] arr, Object[] arr2) {
		if (arr == null || arr.length == 0) {
			return;
		}
		//建立大顶堆
		for (int i = arr.length / 2; i >= 0; i--) {
			heapAdjust(arr, i, arr.length - 1);
		}

		for (int i = arr.length - 1; i >= 0; i--) {
			swap(arr, arr2, 0, i);
			heapAdjust(arr, 0, i - 1);
		}
	}

	/**
	 * 堆筛选，除了start之外，start~end均满足大顶堆的定义。
	 * 调整之后start~end称为一个大顶堆。
	 *
	 * @param arr   待调整数组
	 * @param start 起始指针
	 * @param end   结束指针
	 */
	private static void heapAdjust(double[] arr, int start, int end) {
		double temp = arr[start];
		for (int i = 2 * start + 1; i <= end; i *= 2) {
			//左右孩子的节点分别为2*i+1,2*i+2

			//选择出左右孩子较小的下标
			if (i < end && arr[i] < arr[i + 1]) {
				i++;
			}
			if (temp >= arr[i]) {
				//已经为大顶堆，=保持稳定性。
				break;
			}
			//将子节点上移
			arr[start] = arr[i];
			//下一轮筛选
			start = i;
		}
		//插入正确的位置
		arr[start] = temp;
	}

	/**
	 * 堆排序(从大到小)
	 *
	 * @param arr
	 * @param arr2
	 */
	public static void minHeapSort(int[] arr, Object[] arr2) {
		int n = arr.length;
		makeMinHeap(arr, n, arr2);
		for (int i = n - 1; i > 0; i--) {
			swap(arr, arr2, 0, i);
			minHeapFixdown(arr, 0, i, arr2);
		}
	}

	private static void makeMinHeap(int[] arr, int n, Object[] arr2) {
		for (int i = (n - 1) / 2; i >= 0; i--) {
			minHeapFixdown(arr, i, n, arr2);
		}
	}

	private static void minHeapFixdown(int[] arr, int i, int n, Object[] arr2) {
		//从i节点开始调整,n为节点总数 从0开始计算 i节点的子节点为 2*i+1, 2*i+2
		//子节点
		int j = 2 * i + 1;
		while (j < n) {
			//在左右子节点中寻找最小的
			if (j + 1 < n && arr[j + 1] < arr[j]) {
				j++;
			}
			if (arr[i] <= arr[j]) {
				break;
			}
			//较大节点下移
			swap(arr, arr2, i, j);
			i = j;
			j = 2 * i + 1;
		}
	}

	/**
	 * 堆排序(从大到小)
	 *
	 * @param arr
	 * @param arr2
	 */
	public static void minHeapSort(double[] arr, Object[] arr2) {
		int n = arr.length;
		makeMinHeap(arr, n, arr2);
		for (int i = n - 1; i > 0; i--) {
			swap(arr, arr2, 0, i);
			minHeapFixdown(arr, 0, i, arr2);
		}
	}

	private static void makeMinHeap(double[] arr, int n, Object[] arr2) {
		for (int i = (n - 1) / 2; i >= 0; i--) {
			minHeapFixdown(arr, i, n, arr2);
		}
	}

	private static void minHeapFixdown(double[] arr, int i, int n, Object[] arr2) {
		//从i节点开始调整,n为节点总数 从0开始计算 i节点的子节点为 2*i+1, 2*i+2
		//子节点
		int j = 2 * i + 1;
		while (j < n) {
			//在左右子节点中寻找最小的
			if (j + 1 < n && arr[j + 1] < arr[j]) {
				j++;
			}
			if (arr[i] <= arr[j]) {
				break;
			}
			//较大节点下移
			swap(arr, arr2, i, j);
			i = j;
			j = 2 * i + 1;
		}
	}

}
