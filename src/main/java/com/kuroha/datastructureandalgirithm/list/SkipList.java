package com.kuroha.datastructureandalgirithm.list;

/**
 * @author kuroha
 */
public class SkipList<T> {

    private static final float SKIPLIST_P = 0.5f;

    private static final int MAX_LEVEL = 16;

    private int levelCount = 1;

    private static class Node<T> {
        private T data;
        private Node forwards[] = new Node[MAX_LEVEL];
        private int maxLevel = 0;
    }

    public T find(int num) {
        return null;
    }

    public void insert(T data,int index) {

    }

    public void insert(T data) {

    }

}
