package com.kuroha.datastructureandalgorithm.netty.netty.util;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Chenyudeng
 */
public final class IdUtil {

    private static final AtomicLong IDX = new AtomicLong();

    private IdUtil() {

    }

    public static long nextId() {
        return IDX.incrementAndGet();
    }
}
