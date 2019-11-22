package com.kuroha.dataStructureAndAlgirithm.list;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author kuroha
 */
public class ArrayList<T> implements Serializable {
    private static final long serialVersionUID = -7508753313914906105L;
    /**
     * 数组数据
     */
    private Object[] data;
    /**
     * 数据量
     */
    private int pos = 0;
    /**
     * 数组大小
     */
    private int size;
    private static final Object[] EMPTY_DATA = {};

    public ArrayList() {
        int limit = 16;
        size = limit;
        data = new Object[limit];
    }
    public ArrayList(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("数组长度必须大于等于0");
        } else if (limit == 0) {
            data = EMPTY_DATA;
        } else {
            size = limit;
            data = new Object[limit];
        }
    }
    
    public ArrayList add(T t) {
        this.checkSize(1);
        data[pos++] = t;
        return this;
    }

    public ArrayList add(T... t) {
        this.checkSize(t.length);
        for (T t1 : t) {
            data[pos++] = t1;
        }
        return this;
    }
    @SuppressWarnings("unchecked")
    public T get(int i) {
        if (i < size) {
            return (T)data[i];
        }
        throw new IndexOutOfBoundsException("数组越界");
    }

    public int size() {
        return pos;
    }

    private void checkSize(int num) {
        if (num + pos <= size) {
            return;
        }
        if (size < 1) {
            size = 10;
        } else {
            size = (int)(size * 1.5);
        }
        data = Arrays.copyOf(data,size);
        checkSize(num);
    }

    @Override
    public String toString() {
        if (pos == 0) {
            return "[]";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        int num = pos -1;
        for (int i = 0; i < num; i++) {
            builder.append(data[i]).append(",");
        }
        builder.append(data[num]).append("]");
        return builder.toString();
    }
}
