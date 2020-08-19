package com.kuroha.algorithm.entity;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用于降低时间复杂度,将list的关键字提取出来作为Map的键.
 *
 * @param <K> 键
 * @param <V> 值
 * @author kuroha
 * @date 2019-04-08 14:08:18
 */
public class SamHashMap<K, V> extends HashMap<K, V> {

	public SamHashMap(String key, List<V> values) {
		super();
		parse(key, values);
	}

	public SamHashMap(int initialCapacity, String key, List<V> values) {
		super(initialCapacity);
		parse(key, values);
	}

	public SamHashMap(int initialCapacity, float loadFactor, String key, List<V> values) {
		super(initialCapacity, loadFactor);
		parse(key, values);
	}

	private void parse(String key, List<V> values) {
		try {
			if (values == null || values.size()==0) {
				return;
			}
			if (values.get(0) instanceof Map){
				for (V value : values) {
					Map<?, ?> map = (Map<?, ?>) value;
					this.put((K) map.get(key), value);
				}
			} else {
				for (V value : values) {
					Class<?> clazz = value.getClass();
					Field field = clazz.getDeclaredField(key);
					field.setAccessible(true);
					this.put((K) field.get(value), value);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
