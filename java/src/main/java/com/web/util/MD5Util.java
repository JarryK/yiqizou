package com.web.util;

import java.security.MessageDigest;

/**
 * MD5加密 工具类
 * */
public class MD5Util {

	// 盐值，用于混交md5
	private static final String slat = "!m*j$uG%p16#";
	
	/**
	 * MD5加密
	 * */
	public static String encrypt(String dataStr) throws Exception{
		dataStr = dataStr + slat;
		MessageDigest m = MessageDigest.getInstance("MD5");
		m.update(dataStr.getBytes("UTF8"));
		byte s[] = m.digest();
		String result = "";
		for (int i = 0; i < s.length; i++) {
			result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
		}
		return result;
	}
	
	
	/*
	public static void main(String[] args) {
		try {
			System.out.println(MD5Util.encrypt("123456"));
			// 0cfefb2f1dd7e4a666e496fce5b85db8
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	*/
	
}
