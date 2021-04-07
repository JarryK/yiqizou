package com.web.security.mapper;

import com.web.security.model.UserSecurity;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;

@org.apache.ibatis.annotations.Mapper
public interface UserSecurityMapper extends Mapper<UserSecurity> {
	
	UserSecurity findUserById(Long id);
	
	UserSecurity findUserByUsername(Long username);
	
	@Select("select max(username) from sys_user_security")
	Long findMaxUsername();
}
