package com.web.security.controller;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.IdUtil;
import com.web.base.RestResult;
import com.web.hotdata.HotDataStore;
import com.web.security.jwt.JwtSecurityProperties;
import com.web.security.jwt.JwtTokenUtils;
import com.web.security.model.qo.AuthUserQo;
import com.web.security.service.IUserSecurityService;
import com.web.util.CaptchaUtil;
import com.web.util.bean.LoginCodeEnum;
import com.wf.captcha.base.Captcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/yqz/auth")
@Api(tags = "Core-权限：系统授权接口")
public class AuthController {
	
	private final IUserSecurityService userSecurityService;
	
	private final JwtTokenUtils jwtTokenUtils;
	private final JwtSecurityProperties properties;
	
	private final HotDataStore hotDataStore;
	
	private CaptchaUtil captchaUtil = new CaptchaUtil();
	
	@ApiOperation("登录授权")
	@PostMapping("/login")
	public RestResult<Object> login(@Validated @RequestBody AuthUserQo authUser) throws Exception{
		// 使用username登录 username为数字账号
		if(!Validator.isNumber(authUser.getUsername())) {
			return RestResult.error("用户名或密码错误");
		}
		return RestResult.success("登录验证成功",userSecurityService.login(authUser));
	}
	
	@SuppressWarnings("serial")
	@ApiOperation("获取验证码")
	@GetMapping(value = "/code")
	public RestResult<Object> getCode(HttpServletRequest request){
		Captcha captcha = captchaUtil.getCaptcha();
		// 当验证码类型为 arithmetic时且长度 >= 2 时，captcha.text()的结果有几率为浮点型
		String captchaValue = captcha.text();
		if (captcha.getCharType() - 1 == LoginCodeEnum.arithmetic.ordinal() && captchaValue.contains(".")) {
            captchaValue = captchaValue.split("\\.")[0];
        }
		String uuid = properties.getCodeKey() + IdUtil.simpleUUID();
		hotDataStore.set(uuid, captchaValue,captchaUtil.loginCode.getExpiration());
		Map<String, Object> imgResult = new HashMap<String, Object>(2) {{
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        // 测试时会返回将结果
		return RestResult.success("获取验证码成功:"+captchaValue,imgResult);
	}
	
	@ApiOperation("退出登录")
	@GetMapping(value = "/logout")
	public RestResult<Object> logout(HttpServletRequest request){
		String username = jwtTokenUtils.getTokenUserName(request);
		hotDataStore.del(username);
		return RestResult.success("退出登录成功");
	}

}
