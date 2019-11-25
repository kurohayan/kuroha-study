package com.kuroha.datastructureandalgirithm.list;

import java.io.Serializable;

/**
 * Lru链表
 * @author kuroha
 */
public class LruLinkedList<T> implements Serializable {

    private static final long serialVersionUID = 4751020602538380292L;

    private int pos = 0;

    private long size;

    private Note<T> first;

    private Note<T> last;

    private static class Note<T> {
        Note<T> pipe;
        T data;
        Note<T> next;
    }

    public LruLinkedList() {
        size = Integer.MAX_VALUE;
    }
    public LruLinkedList(long limit) {
        size = limit;
    }

    public LruLinkedList add(T... t) {
        Note<T> note = new Note<>();
        note.data = t[0];
        if (last == null) {
            first = note;
            last = note;
        } else {
            Note<T> pipe = last;
            for (int i = 1; i < pos; i++) {
                pipe = pipe.next;
            }
            pipe.next = note;
            note.pipe = pipe;
        }
        if (this.deleteContains()) {
            pos++;
        }
        this.deleteContains();
        for (int i = 1; i < t.length; i++) {
            Note<T> next = new Note<>();
            next.data = t[i];
            next.pipe = note;
            note.next = next;
            note = next;
            if (this.deleteContains()) {
                pos++;
            }
        }
        return this;
    }

    private boolean deleteContains() {
        if (pos >= size) {
            first = first.next;
            return false;
        }
        return true;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Note<T> note = first;
        sb.append("[");
        for (int i = 1; i < pos; i++) {
            sb.append(note.data).append(",");
            note = note.next;
        }
        sb.append(note.data).append("]");
        return sb.toString();
    }
}
