package com.web.security.controller;

import com.web.base.RestResult;
import com.web.security.model.qo.ChangePasswordQo;
import com.web.security.service.IUserSecurityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ssg/userSecurity")
@Api(tags = "Core-权限：用户账号接口")
public class UserSecurityController {
	
	private final IUserSecurityService userSecurityService;

	@ApiOperation("查询用户账号信息通过ID")
	@GetMapping("/queryUserSecurityById")
	public RestResult<Object> queryUserSecurityById(Long id){
		return RestResult.success(userSecurityService.queryUserSecurityById(id));
	}
	
	@ApiOperation("查询用户账号信息通过username")
	@GetMapping("/queryUserSecurityByUsername")
	public RestResult<Object> queryUserSecurityByUsername(Long username){
		return RestResult.success(userSecurityService.queryUserSecurityByUsername(username));
	}
	
	@ApiOperation("更改密码")
	@PostMapping("/changePassword")
	public RestResult<Object> changePassword(@Validated @RequestBody ChangePasswordQo qo) throws Exception{
		userSecurityService.changePassword(qo.getUsername(), qo.getOldPassword(), qo.getNewPassword());
		return RestResult.success();
	}
}
