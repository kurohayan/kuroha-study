package com.kuroha.datastructureandalgorithm.algorithm.queue;

import org.junit.Test;

/**
 * @author kuroha
 */
public class LinkedQueueTest {

    @Test
    public void test() {
        LinkedQueue<String> queue = new LinkedQueue<>();
        System.out.println(queue.deQueue());
        System.out.println(queue.enQueue("1"));
        System.out.println(queue.enQueue("2"));
        System.out.println(queue.deQueue());
        System.out.println(queue.deQueue());
        System.out.println(queue.deQueue());
        System.out.println(queue.enQueue("1"));
        System.out.println(queue.enQueue("2"));
        System.out.println(queue.deQueue());
        System.out.println(queue.deQueue());
    }

}