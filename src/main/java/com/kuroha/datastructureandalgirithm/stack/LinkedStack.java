package com.kuroha.datastructureandalgirithm.stack;

/**
 * 链式栈
 * @author kuroha
 */
public class LinkedStack<T> {

    private Note<T> first;
    private Note<T> last;
    private long num = 0;
    private long size;

    static class Note<T> {
        Note<T> pipe;
        T data;
        Note<T> next;
        Note(Note<T> pipe, T data, Note<T> next) {
            this.pipe = pipe;
            this.data = data;
            this.next = next;
        }
        Note(Note<T> pipe, T data) {
            this.pipe = pipe;
            this.data = data;
        }
        Note() {
        }
    }

    public LinkedStack() {
        first = new Note<>();
        last = first;
        this.size = Long.MAX_VALUE;
    }

    public LinkedStack(long size) {
        first = new Note<>();
        last = first;
        this.size = size;
    }

    public boolean push(T t) {
        if ((num + 1) == size) {
            return false;
        }
        Note<T> pipe = last;
        Note<T> note = new Note<>(pipe,t);
        pipe.next = note;
        last = note;
        num++;
        return true;
    }

    public T pop() {
        if (num == 0) {
            return null;
        }
        Note<T> note = first.next;
        Note<T> next = first.next.next;
        next.pipe = first;
        first.next = next;
        note.pipe = null;
        note.next = null;
        return note.data;
    }

}
