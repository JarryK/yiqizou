package com.web.security.service.impl;

import com.web.security.mapper.RoleMapper;
import com.web.security.model.Role;
import com.web.security.service.IRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Primary
public class RoleServiceImpl implements IRoleService {

	private final RoleMapper roleMapper;
	
	@Override
	public int insert(Role role) {
		return roleMapper.insert(role);
	}
	@Override
	public int delete(Role role) {
		return roleMapper.delete(role);
	}
	@Override
	public int update(Role role) {
		return roleMapper.updateByPrimaryKey(role);
	}
	@Override
	public List<Role> query(Role role) {
		return roleMapper.select(role);
	}
}
