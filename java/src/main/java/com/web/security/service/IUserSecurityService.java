package com.web.security.service;

import com.web.security.model.UserSecurity;
import com.web.security.model.qo.AuthUserQo;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Map;

//Spring Security 必须实现 UserDetailsService 接口
public interface IUserSecurityService extends UserDetailsService {
	
	/**
	 * 注册用户
	 * @throws Exception 
	 * */
	int signUp(UserSecurity userSecurity) throws Exception;
	
	Map<String, Object> login(AuthUserQo authUser)  throws Exception;
	
	int delete(UserSecurity userSecurity);
	
	int update(UserSecurity userSecurity);
	
	UserSecurity queryUserSecurityById(Long id);
	
	UserSecurity queryUserSecurityByUsername(Long username);
	
	/**
	 * 更改密码
	 * @param username 用户名
	 * @param oldPassword 旧密码（明文）
	 * @param newPassword 新密码（明文）
	 * */
	int changePassword(Long username, String oldPassword, String newPassword) throws Exception;
	
}
