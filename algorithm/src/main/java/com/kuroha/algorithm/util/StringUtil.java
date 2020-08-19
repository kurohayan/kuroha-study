package com.kuroha.algorithm.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author livecat
 * @date 2019-03-04 16:34:40
 */
@Slf4j
public class StringUtil {

	private static final String NOT_STRING = "null";

	/**
	 * 判断不为空
	 *
	 * @param str
	 * @return
	 */
	public static boolean isNotBlank(String str) {
		return StringUtils.isNotBlank(str);
	}

	/**
	 * 字符串为 null 或者内部字符全部为 ' ' '\t' '\n' '\r' 这四类字符时返回 true
	 *
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return StringUtils.isBlank(str);
	}
/**
	 * 字符串为 null 或者内部字符全部为 ' ' '\t' '\n' '\r' 这四类字符时返回 true
	 *
	 * @return
	 */
	public static boolean isBlankObjectField(Object object) {
		Class<?> clazz = object.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				Object value = field.get(object);
				if (isBlank(String.valueOf(value))){
					return true;
				}
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	public static boolean isBlank(String... strs) {
		if (strs == null) {
			return true;
		}
		int len = strs.length;
		if (len == 0) {
			return true;
		}
		for (String str : strs) {
			if (isBlank(str)) {
				return true;
			}
		}
		return false;
	}

	public static boolean isNotBlank(String... strs) {
		if (strs == null) {
			return true;
		}
		int len = strs.length;
		if (len == 0) {
			return false;
		}
		for (String str : strs) {
			if (isBlank(str)) {
				return false;
			}
		}
		return true;
	}

	public static boolean isNull(Object... objects) {
		return !isNotNull(objects);
	}

	public static boolean isNotNull(Object... objects) {
		if (objects == null) {
			return false;
		}
		int len = objects.length;
		if (len == 0) {
			return false;
		}
		for (Object object : objects) {
			if (object == null) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 拼接字符串
	 *
	 * @param strs
	 * @return
	 */
	public static String splicingString(Object... strs) {
		StringBuilder builder = new StringBuilder();
		for (Object str : strs) {
			builder.append(str);
		}
		return builder.toString();
	}

	/**
	 * 首字母变小写
	 *
	 * @param str 需要转换的字符串
	 * @return 转换后的字符串
	 */
	public static String firstCharToLowerCase(String str) {
		char firstChar = str.charAt(0);
		if (firstChar >= 'A' && firstChar <= 'Z') {
			char[] arr = str.toCharArray();
			arr[0] += ('a' - 'A');
			return new String(arr);
		}
		return str;
	}

	/**
	 * 首字母变大写
	 *
	 * @param str 需要转换的字符串
	 * @return 转换后的字符串
	 */
	public static String firstCharToUpperCase(String str) {
		char firstChar = str.charAt(0);
		if (firstChar >= 'a' && firstChar <= 'z') {
			char[] arr = str.toCharArray();
			arr[0] -= ('a' - 'A');
			return new String(arr);
		}
		return str;
	}

	/**
	 * 字符串相似比较
	 *
	 * @param a 字符串a
	 * @param b 字符串b
	 * @return
	 */
	public static boolean slowEquals(String a, String b) {
		byte[] aBytes = (a != null ? a.getBytes() : null);
		byte[] bBytes = (b != null ? b.getBytes() : null);
		return HashUtil.slowEquals(aBytes, bBytes);
	}

	/**
	 * 获取uuid并转换成大写字母
	 *
	 * @return
	 */
	public static String uuid() {
		return UUID.randomUUID().toString().replace("-", "").toUpperCase();
	}
	public static String uuid(int num) {
		return UUID.randomUUID().toString().replace("-", "").substring(0,num).toUpperCase();
	}

	/**
	 * 将异常类转化成字符串
	 *
	 * @param e 异常类
	 * @return 异常信息
	 */
	public static String getFromException(Exception e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw, true));
		return sw.toString();
	}

	/**
	 * 将异常类转化成字符串
	 *
	 * @param e 异常类
	 * @return 异常信息
	 */
	public static String getFromException(Throwable e) {
		StringWriter sw = new StringWriter();
		e.printStackTrace(new PrintWriter(sw, true));
		return sw.toString();
	}

	private static final int EXP = 1;
	// 简单加密
	public static String encrypt(String str) {
		StringBuffer buf = new StringBuffer();
		char[] cs = str.toCharArray();
		char a;
		for (int i = 0; i < cs.length; i++) {
			a = (char) ((int) cs[i] + EXP);
			buf.append(a);
		}
		return buf.toString();
	}

	/**
	 * 清楚img标签
	 * @param content
	 * @return
	 */
	public static String clearLabel2(String content) {
		return content.replaceAll("&gt;",">")
				.replaceAll("&lt;","<")
				.replaceAll("<p>","")
				.replaceAll("</p>","")
				.replaceAll("<script[^>]*?>[\\s\\S]*?</script>","")
				.replaceAll("<style[^>]*?>[\\s\\S]*?</style>","")
				.replaceAll("<[^>]+>","");
	}

	/**
	 * 获取UUID
	 */
	public static String getUUID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-","");
	}
	/**
	 * 获取UUID
	 */
	public static String getUUID(int length){
		if (length <1) {
			length = 1;
		}
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-","").substring(0,length);
	}

	/**
	 * 判断是否为NULL 和 0 和 “ ”
	 *
	 * @param obj
	 * @return
	 */
	public static boolean isNullOrEmptyOrZero(Object obj) {
		if (obj == null) {
			return true;
		}
		if (obj instanceof Long) {
			Long value = (Long) obj;
			if (value <= 0)
				return true;
		} else if (obj instanceof Integer) {
			Integer value = (Integer) obj;
			if (value <= 0)
				return true;
		} else if (obj instanceof Double) {
			Double value = (Double) obj;
			if (value <= 0)
				return true;
		} else if (obj instanceof Short) {
			Short value = (Short) obj;
			if (value <= 0)
				return true;
		} else if (obj instanceof String) {
			String value = (String) obj;
			if (StringUtils.isEmpty(value) || StringUtils.isBlank(value))
				return true;
		} else if (obj instanceof List) {
			List<?> value = (List<?>) obj;
			if (value.size() <= 0)
				return true;
		}

		return false;
	}

	/**
	 * @Description (计算百分比)
	 * @param x
	 * @param total
	 * @return
	 */
	public static String getPercent(int x, int total) {
		// 接受百分比的值
		String result = "";
		if (total <= 0) {
			return "0.00%";
		}
		double x_double = x * 1.0;
		double tempresult = x_double / total;
		// NumberFormat nf = NumberFormat.getPercentInstance(); 注释掉的也是一种方法
		// nf.setMinimumFractionDigits( 2 ); 保留到小数点后几位
		DecimalFormat df1 = new DecimalFormat("0.00%"); // ##.00%
		// 百分比格式，后面不足2位的用0补齐
		// result=nf.format(tempresult);
		result = df1.format(tempresult);
		return result;
	}

	public static JSONArray splitString(String content, int size) {
		if (StringUtil.isBlank(content)) {
			return null;
		}
		int num = 1;
		JSONArray jsonArray = new JSONArray();
		String[] contents = null;
		if (content.contains("\r\n")) {
			contents = content.split("\r\n");
		} else if (content.contains("\r")) {
			contents = content.split("\r\n");
		}else if (content.contains("\n")) {
			contents = content.split("\r\n");
		}
		if (contents == null) {
			int sum = content.length();
			for (int i = 0; i < sum; i+=size) {
				JSONObject data = new JSONObject();
				String sentence;
				if (i + size < sum) {
					sentence = content.substring(i, i + size);
				} else {
					sentence = content.substring(i, sum);
				}
				if (StringUtils.isBlank(sentence)) {
					continue;
				}
				data.put("num", num++);
				data.put("sentence", sentence);
				jsonArray.add(data);
			}
		}
		if (contents == null) {
			return jsonArray;
		}
		for (String paragraph : contents) {
			int sum = paragraph.length();
			for (int i = 0; i < sum; i+=size) {
				JSONObject data = new JSONObject();
				String sentence;
				if (i + size < sum) {
					sentence = content.substring(i, i + size);
				} else {
					sentence = content.substring(i, sum);
				}
				if (StringUtils.isBlank(sentence)) {
					continue;
				}
				data.put("num", num++);
				data.put("sentence", sentence);
				jsonArray.add(data);
			}
		}
		return jsonArray;
	}

	/**
	 * 统计字数（过滤空格、回车、换行等字符）
	 *
	 * @return int
	 * @author SunC 2016年7月2日 下午3:59:19
	 */
	public static int countWords(String content) {
		content = content.replaceAll(" ", "").replaceAll("\r", "").replaceAll("\n", "").replaceAll("\t", "")
				.replaceAll("\f", "").replaceAll("\b", "");
		return content.length();
	}

	public static String money2String(float money) {
		String str = "" + money;
		if (str.indexOf('.') == -1) {
			str = str + ".00";
		} else {
			str = str + "00";
		}
		str = str.substring(0, str.indexOf('.') + 3);
		return str;
	}

    public static boolean isEmail(String mail) {
        String emailPattern = "^([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)*@([a-zA-Z0-9]*[-_]?[a-zA-Z0-9]+)+[\\.][A-Za-z]{2,3}([\\.][A-Za-z]{2})?$";
        Pattern p = Pattern.compile(emailPattern);
        Matcher m = p.matcher(mail);
        return m.matches();
    }

    public static boolean isPhone(String phone) {
        if (StringUtil.isBlank(phone)) {
            return false;
        }
        String mobilePhonePattern = "^([1][3,4,5,7,8][0-9]{9})$";
        Pattern p = Pattern.compile(mobilePhonePattern);
        Matcher m = p.matcher(phone);
        return m.matches();
    }
}
