package com.kuroha.algorithm.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密
 *
 * @author kuroha
 */
public class Md5Util {
	/**
	 * md5加密
	 * @param str
	 * @return
	 */
	public static String crypt(String str) {
		if (StringUtil.isBlank(str)) {
			throw new IllegalArgumentException("String to encript cannot be null or zero length");
		}
		StringBuilder hexString = new StringBuilder();
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			byte[] hash = md.digest();
			for (byte b : hash) {
				if ((0xff & b) < 0x10) {
					hexString.append(0).append(Integer.toHexString((0xFF & b)));
				} else {
					hexString.append(Integer.toHexString(0xFF & b));
				}
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return hexString.toString();
	}

}
