package com.kuroha.datastructureandalgirithm.queue;

/**
 * 链式队列
 * @author kuroha
 * @date 2019-11-26 10:40:16
 */
public class LinkedQueue<T> {

    private Note<T> first;
    private Note<T> last;
    private int size;
    private int num;

    static class Note<T> {
        T data;
        Note<T> next;
        public Note() {

        }
        public Note(T t) {
            data = t;
        }
        public Note(T t,Note<T> note) {
            data = t;
            next = note;
        }
    }

    public LinkedQueue(int size) {
        this.size = size;
        num = 0;
    }

    public LinkedQueue() {
        this.size = 16;
        num = 0;
    }

    public boolean enQueue(T t) {
        if (num == size) {
            return false;
        }
        Note<T> note = new Note<>(t);
        if (num++ == 0) {
            first = note;
            last = note;
        } else {
            last.next = note;
        }
        return true;
    }

    public T deQueue() {
        if (num == 0) {
            return null;
        }
        Note<T> note = this.first;
        first = first.next;
        note.next = null;
        num--;
        return note.data;
    }

}
