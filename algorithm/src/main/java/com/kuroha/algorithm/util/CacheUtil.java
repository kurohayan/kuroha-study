package com.kuroha.algorithm.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @author Chenyudeng
 */
@Slf4j
public class CacheUtil {

    private static final LoadingCache<String, Object> cache = CacheBuilder.newBuilder()
            .maximumSize(20000)
            .expireAfterWrite(120, TimeUnit.MINUTES)
            .build(new CacheLoader<String, Object>() {
                @Override
                public Object load(String str) throws Exception {
                    return null;
                }
            });

    public static void put(String key, Object value) {
        if (StringUtil.isBlank(key)) {
            return;
        }
        if (StringUtil.isNull(value)) {
            return;
        }
        cache.put(key,value);
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(String key , Class<T> clazz) {
        if (StringUtil.isBlank(key)) {
            return null;
        }
        try {
            return (T) cache.get(key);
        } catch (Exception e) {
            log.debug("不存在key");
        }
        return null;
    }

    public static void del(String key) {
        if (StringUtil.isBlank(key)) {
            return;
        }
        try {
            cache.invalidate(key);
        }catch (Exception e) {
            log.debug("key 已经被删除:" + key);
        }
    }
}
