package com.kuroha.datastructureandalgorithm.algorithm.queue;

import com.kuroha.algorithm.queue.ArrayQueue;
import org.junit.Test;

/**
 * @author kuroha
 */
public class ArrayQueueTest {

    @Test
    public void test() {
        ArrayQueue<String> queue = new ArrayQueue<>(2);
        System.out.println(queue.deQueue());
        queue.enQueue("1");
        queue.enQueue("2");
        System.out.println(queue.deQueue());
        System.out.println(queue.deQueue());
        queue.enQueue("3");
        queue.enQueue("4");
        System.out.println(queue.deQueue());
        System.out.println(queue.deQueue());
    }
}
