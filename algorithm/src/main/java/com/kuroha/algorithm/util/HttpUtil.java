package com.kuroha.algorithm.util;

import com.kuroha.algorithm.entity.HttpDeleteWithBody;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.*;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author kuroha
 * @date 2019-03-06 17:25:12
 */
@Slf4j
public class HttpUtil {

	/**
	 * 向指定URL发送GET方法的请求
	 *
	 * @param url   发送请求的URL
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url) {
		StringBuilder builder = new StringBuilder();
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; UTF-8");
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
			String line;
			while ((line = in.readLine()) != null) {
				builder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return builder.toString();
	}

	/**
	 * 向指定URL发送GET方法的请求
	 *
	 * @param url   发送请求的URL
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url,Map<String,String> headers) {
		StringBuilder builder = new StringBuilder();
		BufferedReader in = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; UTF-8");
			headers.forEach((k,v) -> connection.setRequestProperty(k,v));
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8));
			String line;
			while ((line = in.readLine()) != null) {
				builder.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			if (in != null) {
				try {
					in.close();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		}
		return builder.toString();
	}
	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url   发送请求的 URL
	 * @param param 请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param,Map<String,String> headers) {
		log.info("url:" + url);
		log.info("param:" + param);
		PrintWriter out = null;
		BufferedReader in = null;
		StringBuilder sb = new StringBuilder();
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			conn.setRequestProperty("Accept-Charset", "utf-8");
			conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			headers.forEach((k,v) -> conn.setRequestProperty(k,v));
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			log.info("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 构建请求参数
	 *
	 * @param params
	 * @return
	 */
	public static String getParam(Map<String, Object> params) {
		StringBuffer sb = new StringBuffer();
		String str = "";
		if (params != null) {
			for (Map.Entry<String, Object> e : params.entrySet()) {
				sb.append(e.getKey());
				sb.append("=");
				sb.append(e.getValue());
				sb.append("&");
			}
			str = sb.substring(0, sb.length() - 1);

		}
		return str;
	}

	/**
	 * post请求 body提交
	 * @author kuroha
	 * @param url   发送请求的url
	 * @param param body中要发送的信息 可以是对象或是Map
	 * @return 返回字符串
	 */
	public static String doPost(String url, Object param) {
		return doPost(url,null,param);
	}
	/**
	 * post请求 body提交
	 * @author kuroha
	 * @param url   发送请求的url
	 * @param param body中要发送的信息 可以是对象或是Map
	 * @return 返回字符串
	 */
	public static String doPost(String url, Map<String,String> headers, Object param) {
		return doPost(url, headers, param,null);
	}
	/**
	 * post请求 body提交
	 * @author kuroha
	 * @param url   发送请求的url
	 * @param param body中要发送的信息 可以是对象或是Map
	 * @return 返回字符串
	 */
	public static String doPost(String url, Map<String,String> headers, Object param,String type) {
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost(url);
		if (headers != null) {
			headers.forEach(httpPost::addHeader);
		}
		if (StringUtil.isBlank(type)) {
			if (param instanceof String) {
				return doSend(client, param.toString(), httpPost);
			} else {
				List<NameValuePair> list = getEntry(param);
				return doSend(client, list, httpPost);
			}
		} else if (Const.HTTP_FILE.equals(type)) {
			HttpEntity entity = getFileEntry(param,headers);
			return doSend(client,entity,httpPost);
		} else {
			return null;
		}
	}

	/**
	 * put请求 body提交
	 * @author kuroha
	 * @param url   发送请求的url
	 * @param param body中要发送的信息 可以是对象或是Map
	 * @return 返回字符串
	 */
	public static String doPut(String url, Object param) {
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpPut httpPut = new HttpPut(url);
		if (param instanceof String) {
			return doSend(client, param.toString(), httpPut);
		} else {
			List<NameValuePair> list = getEntry(param);
			return doSend(client, list, httpPut);
		}
	}

	/**
	 * delete请求 body提交
	 * @author kuroha
	 * @param url   发送请求的url
	 * @param param body中要发送的信息 可以是对象或是Map
	 * @return 返回字符串
	 */
	public static String doDelete(String url, Object param) {
		CloseableHttpClient client = HttpClientBuilder.create().build();
		HttpDeleteWithBody httpDeleteWithBody = new HttpDeleteWithBody(url);
		if (param instanceof String) {
			return doSend(client, param.toString(), httpDeleteWithBody);
		} else {
			List<NameValuePair> list = getEntry(param);
			return doSend(client, list, httpDeleteWithBody);
		}
	}


	/**
	 * 发送请求
	 * @au
	 * @param client
	 * @param list
	 * @param httpSend
	 * @return
	 */
	private static String doSend(CloseableHttpClient client, List<NameValuePair> list, HttpEntityEnclosingRequestBase httpSend) {
		try {
			UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list, "UTF-8");
			return doSend(client,entity,httpSend);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 发送请求
	 * @au
	 * @param client
	 * @param body
	 * @param httpSend
	 * @return
	 */
	private static String doSend(CloseableHttpClient client, String body, HttpEntityEnclosingRequestBase httpSend) {
		StringEntity entity = new StringEntity(body, "UTF-8");
		return doSend(client,entity,httpSend);
	}

	/**
	 * 发送请求
	 * @au
	 * @param client
	 * @param entity
	 * @param httpSend
	 * @return
	 */
	private static String doSend(CloseableHttpClient client,HttpEntity entity, HttpEntityEnclosingRequestBase httpSend) {
		try {
			httpSend.setEntity(entity);
			CloseableHttpResponse response = client.execute(httpSend);
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				HttpEntity httpEntity = response.getEntity();
				return EntityUtils.toString(httpEntity);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 构建请求参数
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<NameValuePair> getEntry(Object param) {
		List<NameValuePair> list = new ArrayList<>();
		Class<?> clazz = param.getClass();
		Field[] fields = clazz.getDeclaredFields();
		if (param instanceof Map) {
			Map<String,Object> map = (Map)param;
//            JSONObject jsonObject = JSON.parseObject(JSON.toJSONString(param));
			Set<Map.Entry<String, Object>> entries = map.entrySet();
			for (Map.Entry<String, Object> entry : entries) {
				list.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
			}
		} else {
			for (Field field : fields) {
				try {
					field.setAccessible(true);
					if (StringUtil.isNotNull((String) field.get(param))) {
						list.add(new BasicNameValuePair(field.getName(), String.valueOf(field.get(param))));
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}

	/**
	 * 构建请求参数
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static HttpEntity getFileEntry(Object param) {
		Class<?> clazz = param.getClass();
		Field[] fields = clazz.getDeclaredFields();
		MultipartEntityBuilder builder = MultipartEntityBuilder.create()
				.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
				.setCharset(StandardCharsets.UTF_8)
				.setContentType(ContentType.MULTIPART_FORM_DATA);
		if (param instanceof Map) {
			Set<Map.Entry<String, Object>> entries = ((Map) param).entrySet();
			for (Map.Entry<String, Object> entry : entries) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (value instanceof CommonsMultipartFile) {
					CommonsMultipartFile multipartFile = (CommonsMultipartFile) value;
					builder.addBinaryBody(key,multipartFile.getBytes(), ContentType.APPLICATION_OCTET_STREAM,multipartFile.getOriginalFilename());
				} else {
					builder.addTextBody(key, String.valueOf(value));
				}
			}
		} else {
			for (Field field : fields) {
				try {
					field.setAccessible(true);
					String key = field.getName();
					Object value = field.get(param);
					if (StringUtil.isNotNull(field.get(param))) {
						if (value instanceof CommonsMultipartFile) {
							builder.addBinaryBody(key,((CommonsMultipartFile) value).getBytes());
						} else {
							builder.addTextBody(key, String.valueOf(value));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return builder.build();
	}
	/**
	 * 构建请求参数
	 *
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static HttpEntity getFileEntry(Object param,Map<String,String> headers) {
		Class<?> clazz = param.getClass();
		Field[] fields = clazz.getDeclaredFields();
		String contentType = headers.get("Content-Type");
		MultipartEntityBuilder builder = MultipartEntityBuilder.create()
				.setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
				.setCharset(StandardCharsets.UTF_8);
		if (StringUtil.isBlank(contentType)) {
			builder.setContentType(ContentType.MULTIPART_FORM_DATA);
		} else {
			builder.setContentType(ContentType.parse(contentType));
		}
		if (param instanceof Map) {
			Set<Map.Entry<String, Object>> entries = ((Map) param).entrySet();
			for (Map.Entry<String, Object> entry : entries) {
				String key = entry.getKey();
				Object value = entry.getValue();
				if (value instanceof CommonsMultipartFile) {
					CommonsMultipartFile multipartFile = (CommonsMultipartFile) value;
					builder.addBinaryBody(key,multipartFile.getBytes(), ContentType.APPLICATION_OCTET_STREAM,multipartFile.getOriginalFilename());
				} else {
					builder.addTextBody(key, String.valueOf(value));
				}
			}
		} else {
			for (Field field : fields) {
				try {
					field.setAccessible(true);
					String key = field.getName();
					Object value = field.get(param);
					if (StringUtil.isNotNull(field.get(param))) {
						if (value instanceof CommonsMultipartFile) {
							builder.addBinaryBody(key,((CommonsMultipartFile) value).getBytes());
						} else {
							builder.addTextBody(key, String.valueOf(value));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return builder.build();
	}
}
