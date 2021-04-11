package com.web.security.service.impl;

import com.web.hotdata.HotDataStore;
import com.web.mapper.UserMapper;
import com.web.model.User;
import com.web.security.custom.JechoPasswordEncoder;
import com.web.security.jwt.JwtSecurityProperties;
import com.web.security.jwt.JwtTokenUtils;
import com.web.security.mapper.UserSecurityMapper;
import com.web.security.model.UserSecurity;
import com.web.security.model.qo.AuthUserQo;
import com.web.security.service.IUserSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Primary
public class UserSecurityServiceImpl implements IUserSecurityService{
	
	private final UserSecurityMapper userSecurityMapper;
	private final UserMapper userMapper;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenUtils jwtTokenUtils;
	private final JwtSecurityProperties properties;
	private final HotDataStore hotDataStore;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserSecurity userSecurity = userSecurityMapper.findUserByUsername((Long.valueOf(username)));
        if (userSecurity == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        return userSecurity;
	}

	@Override
	public synchronized int signUp(UserSecurity userSecurity) throws Exception {
		String password = userSecurity.getPassword();
		if(!verifyPassword(password)) {
			throw new Exception("新密码格式有误，密码长度为6至20位");
		}
        PasswordEncoder encoder = new JechoPasswordEncoder();
        userSecurity.setPassword(encoder.encode(password));
        userSecurity.setCreateBy("");
        userSecurity.setAccountNonExpired(true);
        userSecurity.setAccountNonLocked(true);
        userSecurity.setEnabled(true);
        userSecurity.setCredentialsNonExpired(true);
		return userSecurityMapper.insert(userSecurity);
	}
	
	@SuppressWarnings("serial")
	@Override
	public Map<String, Object> login(@Validated AuthUserQo authUser) throws Exception {
		String password = authUser.getPassword();
		// Rsa密码解密
        // password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, authUser.getPassword());
		// 查询验证码
		String code = (String) hotDataStore.get(authUser.getUuid());
		// 清除验证码
		hotDataStore.del(authUser.getUuid());
//		if(StringUtils.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)){
//			throw new Exception("验证码错误");
//		}
		// 密码校验
		UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authUser.getUsername(), password);
		// 用户名和密码校验
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
		// 校验成功将身份信息放入SecurityContextHolder
		SecurityContextHolder.getContext().setAuthentication(authentication);
		// 创建Token
		String token = jwtTokenUtils.createToken(authentication);
		User userInfo = new User();
		userInfo.setUserId(Long.valueOf(authentication.getName()));
		userInfo = userMapper.selectByPrimaryKey(Long.parseLong(authentication.getName()));
		hotDataStore.set(authUser.getUsername() + "_info",userInfo);
		// 将Token信息放入缓存,如果之前有相同账号登录过的话，新的token会覆盖原token，原token不可用，相当于自动踢掉已登录用户
		hotDataStore.set(authUser.getUsername().toString(), token,properties.getTokenValidityInSeconds());
		final UserSecurity userSecurity = (UserSecurity) authentication.getPrincipal();
		User finalUserInfo = userInfo;
		Map<String, Object> authInfo = new HashMap<String, Object>(3) {{
            put("token", properties.getTokenStartWith() + token);
            put("user", userSecurity);
            put("user_info", finalUserInfo);
        }};
		return authInfo;
	}

	@Override
	public int delete(UserSecurity userSecurity) {
		return userSecurityMapper.deleteByPrimaryKey(userSecurity);
	}

	@Override
	public int update(UserSecurity userSecurity) {
		userSecurity.setUpdateBy(userSecurity.getUsername());
		return userSecurityMapper.updateByPrimaryKey(userSecurity);
	}

	@Override
	public UserSecurity queryUserSecurityById(Long id) {
		return userSecurityMapper.findUserById(id);
	}
	
	@Override
	public UserSecurity queryUserSecurityByUsername(Long username) {
		return userSecurityMapper.findUserByUsername(username);
	}

	@Override
	public int changePassword(Long username,String oldPassword,String newPassword) throws Exception {
		UserSecurity userSecurity = userSecurityMapper.findUserByUsername(username);
		if(null == userSecurity) {
			throw new Exception("不存在的账号");
		}
		PasswordEncoder encoder = new JechoPasswordEncoder();
		if(!encoder.matches(oldPassword,userSecurity.getPassword())) {
			throw new Exception("旧密码错误");
		}
		if(!verifyPassword(newPassword)) {
			throw new Exception("新密码格式有误，密码长度为6至20位");
		}
		userSecurity.setPassword(encoder.encode(newPassword));
		return userSecurityMapper.updateByPrimaryKey(userSecurity);
	}
	
	/**
	 * 校验密码格式是否符合规范
	 * @param password
	 * */
	private boolean verifyPassword(String password) {
		if(null != password && password.length() > 5 && password.length() < 21) {
			return true;
		}
		return false;
	}


}
