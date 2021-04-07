package com.web.security.model.qo;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class AuthUserQo {

	@NotBlank(message = "用户名不可为空")	// 此注解表明字段不能为null,也不能为空字符串
    private String username;

	@NotBlank(message = "密码不可为空")
    private String password;

    private String code;
	
	private String uuid = "";

}
