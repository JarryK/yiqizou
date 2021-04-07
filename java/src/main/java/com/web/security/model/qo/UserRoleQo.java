package com.web.security.model.qo;

import com.web.security.model.UserRole;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel(value="用户角色参数类",description="用户角色参数类" )
public class UserRoleQo extends UserRole {

	@NotNull(message = "用户ID不可为空")
	private Long userId;
	
	@NotNull(message = "角色ID不可为空")
	private Long roleId;
}
