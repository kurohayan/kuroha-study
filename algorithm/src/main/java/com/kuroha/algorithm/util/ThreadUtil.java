package com.kuroha.algorithm.util;

import java.util.concurrent.*;

/**
 * @author Chenyudeng
 */
public class ThreadUtil {

    private static final ThreadPoolExecutor EXECUTOR = new ThreadPoolExecutor(4, 4, 15, TimeUnit.MINUTES, new LinkedBlockingQueue<>(), runnable -> new Thread(runnable,"ThreadUtil"),new ThreadPoolExecutor.AbortPolicy());

    public static ThreadPoolExecutor getExecutor() {
        return EXECUTOR;
    }

    public static void execute(Runnable runnable) {
        EXECUTOR.execute(runnable);
    }
    public static Future<?> submit(Callable<?> callable) {
        return EXECUTOR.submit(callable);
    }

}
