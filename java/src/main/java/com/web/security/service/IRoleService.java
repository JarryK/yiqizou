package com.web.security.service;

import com.web.security.model.Role;

import java.util.List;

public interface IRoleService {
	
	int insert(Role role);
	
	int delete(Role role);
	
	int update(Role role);
	
	List<Role> query(Role role);
}
