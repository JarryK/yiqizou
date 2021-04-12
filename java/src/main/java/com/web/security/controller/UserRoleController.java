package com.web.security.controller;

import com.web.base.RestResult;
import com.web.hotdata.HotDataStore;
import com.web.security.jwt.JwtTokenUtils;
import com.web.security.mapper.UserRoleMapper;
import com.web.security.model.qo.UserRoleQo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/yqz/userRole")
@Api(tags = "Core-权限：用户角色接口")
public class UserRoleController {
	
	private final UserRoleMapper userRoleMapper;
	
	private final JwtTokenUtils tokenUtils;
	
	private final HotDataStore hotDataStore;

	@ApiOperation("admin:添加用户角色")
	@PreAuthorize("hasAnyRole('admin')")
	@PostMapping("/addUserRole")
	public RestResult<Object> addUserRole(@Validated @RequestBody UserRoleQo userrole, HttpServletRequest request){
		if(userRoleMapper.select(userrole).size() > 0) {
			return RestResult.error("已存在");
		}
		userrole.setCreateBy(tokenUtils.getTokenUserName(request));
		userRoleMapper.insert(userrole);
		// TODO 记得在热容器里面同步修改
//		hotDataStore.set(key, userrole)
		return RestResult.success(userrole);
	}
	
	@ApiOperation("admin:删除用户角色")
	@PreAuthorize("hasAnyRole('admin')")
	@PostMapping("/deleteUserRole")
	public RestResult<Object> deleteUserRole(@Validated @RequestBody UserRoleQo userrole){
		userRoleMapper.delete(userrole);
		// TODO 记得在热容器里面同步修改
		return RestResult.success();
	}
	
}
