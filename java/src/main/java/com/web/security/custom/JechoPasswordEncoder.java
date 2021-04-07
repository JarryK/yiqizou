package com.web.security.custom;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自定义密码加密方式
 * */
public class JechoPasswordEncoder implements PasswordEncoder {
	
	/**
	 * 对密码进行加密
	 * @param rawPassword 用户传入的明文密码
	 * */
	@Override
	public String encode(CharSequence rawPassword) {
		// 这里使用MD5加密的方式，也可以更换成其他方式,如 ：
		// return NoOpPasswordEncoder.getInstance().encode(rawPassword);	// 无加密
		return new BCryptPasswordEncoder().encode(rawPassword);			// bcrypt加密
		//return DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes());
	}

	/**
	 * 对密码进行比对
	 * @param rawPassword 用户登录时传入的密码（明文）
	 * @param encodedPassword 加密后的密码（密文，从数据库中查询而来）
	 * */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		// 这里使用MD5解密的方式，也可以更换成其他方式,如 ：
		// return NoOpPasswordEncoder.getInstance().matches(rawPassword, encodedPassword);	// 无加密
		return new BCryptPasswordEncoder().matches(rawPassword, encodedPassword);	// bcrypt解密
		//return encodedPassword.equals(DigestUtils.md5DigestAsHex(rawPassword.toString().getBytes()));
	}
	
	//测试方法，获取加密后生成的密码串
	public static void main(String[] args) {
		String password = "123456";
		PasswordEncoder encoder = new JechoPasswordEncoder();
		String encryptPw = encoder.encode(password);
		System.out.println("===========加密后的密文===========");
		System.out.println(encryptPw);
		System.out.println("===========密文解密验证===========");
		System.out.println(encoder.matches(password, encryptPw));
	}

}
