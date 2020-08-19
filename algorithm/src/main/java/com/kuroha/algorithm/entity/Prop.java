package com.kuroha.algorithm.entity;

import com.kuroha.algorithm.util.Const;

import java.io.*;
import java.util.Properties;

/**
 * Properties类的增强类
 *
 * @author kuroha
 */
public class Prop {

	private Properties properties = null;

	/**
	 * 解析指定文件
	 *
	 * @param fileName
	 * @return
	 */
	public Prop(String fileName) {
		this(fileName, Const.DEFAULT_ENCODING);
	}

	/**
	 * 解析指定文件,并进行转码
	 *
	 * @param fileName 需要解析的文件的路径
	 * @param encoding 转码后的格式
	 * @return
	 */
	public Prop(String fileName, String encoding) {
		InputStream inputStream = null;
		try {
			inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName);
			if (inputStream == null) {
				throw new IllegalArgumentException("没有找到资源文件: " + fileName);
			}
			properties = new Properties();
			properties.load(new InputStreamReader(inputStream, encoding));
		} catch (IOException e) {
			throw new RuntimeException("加载资源文件失败", e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 解析指定文件
	 *
	 * @param file
	 * @return
	 */
	public Prop(File file) {
		this(file, Const.DEFAULT_ENCODING);
	}

	/**
	 * 解析指定文件,并进行转码
	 *
	 * @param file     需要解析的文件
	 * @param encoding 转码后的格式
	 */
	public Prop(File file, String encoding) {
		if (file == null) {
			throw new IllegalArgumentException("文件为空");
		}
		if (!file.isFile()) {
			throw new IllegalArgumentException("文件没有找到: " + file.getName());
		}

		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(file);
			properties = new Properties();
			properties.load(new InputStreamReader(inputStream, encoding));
		} catch (IOException e) {
			throw new RuntimeException("加载资源文件失败", e);
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 获取指定key的字符串格式
	 *
	 * @param key
	 * @return
	 */
	public String get(String key) {
		return properties.getProperty(key);
	}

	/**
	 * 获取指定key的字符串格式,若没有,则返回设置的默认值
	 *
	 * @param key          键
	 * @param defaultValue 默认值
	 * @return
	 */
	public String get(String key, String defaultValue) {
		return properties.getProperty(key, defaultValue);
	}

	/**
	 * 获取一批使用,分隔的数据
	 *
	 * @param key 键
	 * @return
	 */
	public String[] gets(String key) {
		String property = properties.getProperty(key);
		property = property.replaceAll("，", ",").replaceAll(";", ",").replace(" ", ",");
		return property.split(",");
	}

	/**
	 * 获取一批使用,分隔的数据,若没有则返回默认值
	 *
	 * @param key          键
	 * @param defaultValue 默认值
	 * @return
	 */
	public String[] gets(String key, String defaultValue) {
		String property = properties.getProperty(key, defaultValue);
		property = property.replaceAll("，", ",").replaceAll(";", ",").replace(" ", ",");
		return property.split(",");
	}

	/**
	 * 获取key的值,以int类型返回
	 *
	 * @param key 键
	 * @return
	 */
	public Integer getInt(String key) {
		return getInt(key, null);
	}

	/**
	 * 获取key的值,以int类型返回
	 *
	 * @param key          键
	 * @param defaultValue 默认值
	 * @return
	 */
	public Integer getInt(String key, Integer defaultValue) {
		String value = properties.getProperty(key);
		if (value != null) {
			return Integer.parseInt(value.trim());
		}
		return defaultValue;
	}

	/**
	 * 获取key的值,以long类型返回
	 *
	 * @param key 键
	 * @return
	 */
	public Long getLong(String key) {
		return getLong(key, null);
	}

	/**
	 * 获取key的值,以long类型返回
	 *
	 * @param key          键
	 * @param defaultValue 默认值
	 * @return
	 */
	public Long getLong(String key, Long defaultValue) {
		String value = properties.getProperty(key);
		if (value != null) {
			return Long.parseLong(value.trim());
		}
		return defaultValue;
	}

	/**
	 * 获取key的值以boolean类型展示
	 *
	 * @param key 键
	 * @return
	 */
	public Boolean getBoolean(String key) {
		return getBoolean(key, null);
	}

	/**
	 * 获取key的值以boolean类型展示,若没有,则显示默认值
	 *
	 * @param key          键
	 * @param defaultValue 默认值
	 * @return
	 */
	public Boolean getBoolean(String key, Boolean defaultValue) {
		String value = properties.getProperty(key);
		if (value != null) {
			value = value.toLowerCase().trim();
			if ("true".equals(value)) {
				return true;
			} else if ("false".equals(value)) {
				return false;
			}
			throw new RuntimeException("非boolean类型变量" + value);
		}
		return defaultValue;
	}

	/**
	 * 判断key是否存在
	 *
	 * @param key 键
	 * @return
	 */
	public boolean containsKey(String key) {
		return properties.containsKey(key);
	}

	/**
	 * 获取properties
	 *
	 * @return
	 */
	public Properties getProperties() {
		return properties;
	}
}
