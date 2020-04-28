package com.kuroha.algorithm.stack;

/**
 * 顺序栈
 * @author kuroha
 */
public class ArrayStack<T> {

    Object[] data;

    int num;

    int size;

    public ArrayStack(int size) {
        this.size = size;
        num = 0;
        data = new Object[size];
    }

    public ArrayStack() {
        this.size = 16;
        num = 0;
        data = new Object[size];
    }

    public boolean push(T t) {
        if (num == size) {
            return false;
        }
        data[num++] = t;
        return true;
    }

    @SuppressWarnings("unchecked")
    public T pop() {
        if (num == 0) {
            return null;
        }
        return (T) data[--num];
    }

}
