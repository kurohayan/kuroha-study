package com.kuroha.datastructureandalgorithm.algorithm.list;

import java.util.NoSuchElementException;

/**
 * 单链表
 * @author kuroha
 */
public class OneLinkedList<T> {

    private Note<T> first;

    private Note<T> last;

    private long size;

    static class Note<T> {
        T data;
        Note<T> next;
        public Note() {

        }
        public Note(T data, Note<T> next) {
            this.data = data;
            this.next = next;
        }
    }

    public void add(T t) {
        Note<T> note = new Note<>(t,null);
        final Note<T> l = last;
        last = note;
        if (l == null) {
            first = note;
        } else {
            l.next = note;
        }
        size++;
    }

    public T get(long num) {
        if (num >= size) {
            throw new NoSuchElementException("未找到元素");
        }
        Note<T> note = this.first;
        for (long i = 0; i < num; i++) {
            note = note.next;
        }
        return note.data;
    }

    public void reverse(){
        last = first;
        Note<T> note = first;
        Note<T> pipe = null;
        if (note == null) {
            return;
        }
        while (note.next != null) {
            Note<T> next = note.next;
            note.next = pipe;
            pipe = note;
            note = next;
        }
        note.next = pipe;
        first = note;
    }

    public boolean remove(long index) {
        if (size == 0 || index >= size) {
            throw new IndexOutOfBoundsException("删除链表数据下标越界");
        }
        Note<T> note = first;
        Note<T> next = first.next;
        if (index == 0) {
            first.next = null;
            first = next;
            size--;
        } else {
            for (int i = 1; i < index; i++) {
                note = next;
                next = next.next;
            }
            note.next = next.next;
            next.next = null;
            size--;
            if (index == size) {
                last = note;
            }
        }
        if (first == null) {
            last = null;
        }
        return true;
    }

    public boolean removeLastIndex(long lastNum) {
        long num = size - lastNum - 1;
        return this.remove(num);
    }

    public long index() {
        return size / 2;
    }

    public boolean centralCheck() {
        Note<T> k = first;
        Note<T> s = first;
        while (k.next != null && k.next.next != null) {
            k = k.next.next;
            s = s.next;
            if (k == s) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        if (size == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        Note<T> note = first;
        sb.append("[");
        for (int i = 1; i < size; i++) {
            sb.append(note.data).append(",");
            note = note.next;
        }
        sb.append(note.data).append("]");
        return sb.toString();
    }

}
