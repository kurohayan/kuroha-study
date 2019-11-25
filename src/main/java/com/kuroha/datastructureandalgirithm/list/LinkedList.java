package com.kuroha.datastructureandalgirithm.list;

import java.io.Serializable;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 链表
 * @author kuroha
 */
public class LinkedList<T> implements Serializable {
    private static final long serialVersionUID = 2258704786666970210L;

    int pos = 0;

    int size;

    Note<T> first;

    Note<T> last;

    static class Note<T> {
        Note<T> pipe;
        T data;
        Note<T> next;
    }

    public LinkedList() {
        size = Integer.MAX_VALUE;
        Note<T> note = new Note<>();
        first = note;
        last = note;
    }
    public LinkedList(int limit) {
        size = limit;
        Note<T> note = new Note<>();
        first = note;
        last = note;
    }

    public LinkedList add(T... t) {
        if (t.length + pos > size) {
            throw new ArrayIndexOutOfBoundsException("链表越界");
        }
        Note<T> note = new Note<>();
        note.data = t[0];
        Note<T> pipe = last;
        pipe.next = note;
        note.pipe = pipe;
        last = pipe;
        pos++;
        for (int i = 1; i < t.length; i++) {
            Note<T> next = new Note<>();
            next.data = t[i];
            next.pipe = note;
            note.next = next;
            note = next;
            last = next;
            pos++;
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    public T get(int num) {
        if (num > pos) {
            throw new IndexOutOfBoundsException("数组下标越界");
        }
        Note<T> note = first;
        for (int i = 0; i < num; i++) {
            note = note.next;
        }
        return (T) note.data;
    }

    public boolean palindrome() {
        Note<T> first = this.first.next;
        Note<T> last = this.last.pipe;
        if (first == null || last == null) {
            return true;
        }
        for (int i = 0; i < pos / 2; i++) {
            if (!first.data.equals(last.data)) {
                return false;
            }
            first = first.next;
            last = last.pipe;
        }
        return true;
    }

    @Override
    public String toString() {
        if (pos == 0) {
            return "[]";
        }
        StringBuilder sb = new StringBuilder();
        Note<T> note = first.next;
        sb.append("[");
        for (int i = 1; i < pos; i++) {
            sb.append(note.data).append(",");
            note = note.next;
        }
        sb.append(note.data).append("]");
        return sb.toString();
    }
}
