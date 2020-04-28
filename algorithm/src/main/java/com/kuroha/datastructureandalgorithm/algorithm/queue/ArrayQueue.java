package com.kuroha.datastructureandalgorithm.algorithm.queue;

/**
 * 顺序队列
 * @author kuroha
 * @date 2019-11-26 10:40:09
 */
public class ArrayQueue<T> {

    private Object[] data;

    private final int size;

    private int num;

    private int sum;

    public ArrayQueue() {
        this.size = 16;
        num = 0;
        data = new Object[size];
    }
    public ArrayQueue(int size) {
        this.size = size;
        num = 0;
        data = new Object[size];
    }

    public boolean enQueue(T t) {
        if (sum == size) {
            if (num == 0) {
                return false;
            }
            for (int i = num,j = 0; i < sum; i++,j++) {
                data[j] = data[i];
            }
            sum = sum - num;
            num = 0;
        }
        data[sum++] = t;
        return true;
    }

    @SuppressWarnings("unchecked")
    public T deQueue() {
        if (num == sum) {
            return null;
        }
        return (T) data[num++];
    }

}
