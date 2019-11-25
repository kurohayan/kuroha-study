package com.kuroha.datastructureandalgirithm.list;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * Lru数组
 * @author kuroha
 */
public class LruArrayList<T> implements Serializable {
    private static final long serialVersionUID = -6045063239100809173L;

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

    private Map<T,Integer> map;

    public LruArrayList() {
        int limit = 16;
        size = limit;
        data = new Object[limit];
        map = new HashMap<>(limit);
    }
    public LruArrayList(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("数组长度必须大于等于0");
        } else {
            size = limit;
            data = new Object[limit];
            map = new HashMap<>(limit);
        }
    }

    public LruArrayList add(T t) {
        this.checkSize(t);
        map.remove(data[0]);
        data[0] = t;
        map.put(t,0);
        pos++;
        return this;
    }

    public LruArrayList add(T... t) {
        this.checkSize(t);
        if (t.length > size) {
            throw new ArrayIndexOutOfBoundsException("数组越界");
        }
        int num = t.length -1;
        for (T t1 : t) {
            data[num] = t1;
            map.put(t1,num--);
            pos++;
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

    private void checkSize(T... ts) {
        if (ts.length + pos <= size) {
            return;
        }
        for (T t : ts) {
            Integer num = map.get(t);
            copyShirt(num);
        }
        for (int i = ts.length + pos; i > size; i--) {
            copyShirt(size-1);
        }
    }

    @SuppressWarnings("unchecked")
    private void copyShirt(Integer num) {
        if (num == null) {
            return;
        }
        if (num == 0) {
            map.remove(data[0]);
            data[0] = null;
        }
        for (int i = num; i > 0; i--) {
            data[i] = data[i-1];
            map.put((T)data[i],i);
        }
        data[0] = null;
        pos--;
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
