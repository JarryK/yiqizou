package com.web.security.model.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel(value="更改密码参数类",description="更改密码参数类" )
public class ChangePasswordQo {

	@NotNull(message="用户名不可为空")
	@ApiModelProperty(value = "用户账号,非空，长度最少为6位数的Long数字" ,example = "100000")
	private Long username;
	
	@NotBlank(message="旧密码不可为空")
	@ApiModelProperty(value = "旧密码，非空，6-20位字符串" ,example = "ABC123456")
	private String oldPassword;
	
	@NotBlank(message="新密码不可为空")
	@ApiModelProperty(value = "新密码，非空，6-20位字符串" ,example = "ABC123456")
	private String newPassword;
}
