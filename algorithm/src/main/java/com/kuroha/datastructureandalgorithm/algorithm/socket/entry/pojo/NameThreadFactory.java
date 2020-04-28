package com.kuroha.datastructureandalgorithm.algorithm.socket.entry.pojo;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class NameThreadFactory implements ThreadFactory {

    private final AtomicInteger num = new AtomicInteger(1);

    @Override
    public Thread newThread(Runnable runnable) {
        Thread thread = new Thread(runnable,"test-thread-" + num.getAndIncrement());
        System.out.println(thread.getName() + " has bean created");
        return thread;
    }
}
