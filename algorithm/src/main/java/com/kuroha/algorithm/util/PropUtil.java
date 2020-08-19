package com.kuroha.algorithm.util;

import com.kuroha.algorithm.entity.Prop;

import java.io.File;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 读取配置文件工具类
 *
 * @author kuroha
 */
public class PropUtil {

	private static Prop prop = null;
	private static final ConcurrentHashMap<String, Prop> map = new ConcurrentHashMap<>();

	/**
	 * 不可创建对象
	 */
	private PropUtil() {
	}

	/**
	 * 解析指定文件
	 *
	 * @param fileName
	 * @return
	 */
	public static Prop use(String fileName) {
		return use(fileName, Const.DEFAULT_ENCODING);
	}

	/**
	 * 解析指定文件,并进行转码
	 *
	 * @param fileName 需要解析的文件的路径
	 * @param encoding 转码后的格式
	 * @return
	 */
	public static Prop use(String fileName, String encoding) {
		Prop result = map.get(fileName);
		if (result == null) {
			result = new Prop(fileName, encoding);
			map.put(fileName, result);
			if (PropUtil.prop == null) {
				PropUtil.prop = result;
			}
		}
		return result;
	}

	/**
	 * 解析指定文件
	 *
	 * @param file
	 * @return
	 */
	public static Prop use(File file) {
		return use(file, Const.DEFAULT_ENCODING);
	}

	/**
	 * 解析指定文件,并进行转码
	 *
	 * @param file     需要解析的文件
	 * @param encoding 转码后的格式
	 */
	public static Prop use(File file, String encoding) {
		Prop result = map.get(file.getName());
		if (result == null) {
			result = new Prop(file, encoding);
			map.put(file.getName(), result);
			if (PropUtil.prop == null) {
				PropUtil.prop = result;
			}
		}
		return result;
	}

	/**
	 * 去除map中的prop对象
	 *
	 * @param fileName
	 * @return
	 */
	public static Prop useless(String fileName) {
		Prop previous = map.remove(fileName);
		if (PropUtil.prop == previous) {
			PropUtil.prop = null;
		}
		return previous;
	}

	/**
	 * 清空map
	 */
	public static void clear() {
		prop = null;
		map.clear();
	}

	/**
	 * 获取prop
	 *
	 * @return
	 */
	public static Prop getProp() {
		if (prop == null) {
			throw new IllegalStateException("未进行解析.或者解析失败");
		}
		return prop;
	}

	/**
	 * 从map中获取prop
	 *
	 * @param fileName
	 * @return
	 */
	public static Prop getProp(String fileName) {
		return map.get(fileName);
	}

	/**
	 * 获取指定key的字符串格式
	 *
	 * @param key
	 * @return
	 */
	public static String get(String key) {
		return getProp().get(key);
	}

	/**
	 * 获取指定key的字符串格式,若没有,则返回设置的默认值
	 *
	 * @param key          键
	 * @param defaultValue 默认值
	 * @return
	 */
	public static String get(String key, String defaultValue) {
		return getProp().get(key, defaultValue);
	}

	/**
	 * 获取一批使用,分隔的数据
	 *
	 * @param key 键
	 * @return
	 */
	public static String[] gets(String key) {
		String s = getProp().get(key).replaceAll("，", ",").replaceAll(";", ",").replace(" ", ",");
		return s.split(",");
	}

	/**
	 * 获取一批使用,分隔的数据,若没有则返回默认值
	 *
	 * @param key          键
	 * @param defaultValue 默认值
	 * @return
	 */
	public static String[] gets(String key, String defaultValue) {
		String s = getProp().get(key, defaultValue).replaceAll("，", ",").replaceAll(";", ",").replace(" ", ",");
		return s.split(",");
	}

	/**
	 * 获取key的值,以int类型返回
	 *
	 * @param key 键
	 * @return
	 */
	public static Integer getInt(String key) {
		return getProp().getInt(key);
	}

	/**
	 * 获取key的值,以int类型返回
	 *
	 * @param key          键
	 * @param defaultValue 默认值
	 * @return
	 */
	public static Integer getInt(String key, Integer defaultValue) {
		return getProp().getInt(key, defaultValue);
	}

	/**
	 * 获取key的值,以long类型返回
	 *
	 * @param key 键
	 * @return
	 */
	public static Long getLong(String key) {
		return getProp().getLong(key);
	}

	/**
	 * 获取key的值,以long类型返回
	 *
	 * @param key          键
	 * @param defaultValue 默认值
	 * @return
	 */
	public static Long getLong(String key, Long defaultValue) {
		return getProp().getLong(key, defaultValue);
	}

	/**
	 * 获取key的值以boolean类型展示
	 *
	 * @param key 键
	 * @return
	 */
	public static Boolean getBoolean(String key) {
		return getProp().getBoolean(key);
	}

	/**
	 * 获取key的值以boolean类型展示,若没有,则显示默认值
	 *
	 * @param key          键
	 * @param defaultValue 默认值
	 * @return
	 */
	public static Boolean getBoolean(String key, Boolean defaultValue) {
		return getProp().getBoolean(key, defaultValue);
	}

	/**
	 * 判断key是否存在
	 *
	 * @param key 键
	 * @return
	 */
	public static boolean containsKey(String key) {
		return getProp().containsKey(key);
	}
}


