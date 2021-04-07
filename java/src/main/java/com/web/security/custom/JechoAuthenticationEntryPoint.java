package com.web.security.custom;

import com.google.gson.Gson;
import com.web.base.RestResult;
import com.web.base.StatusCode;
import com.web.security.jwt.JwtTokenUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义认证失败处理类
 */
@Component
@RequiredArgsConstructor
public class JechoAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final JwtTokenUtils jwtTokenUtils;
	
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
    	// 当用户尝试访问安全的REST资源而不提供任何凭据时，将调用此方法发送401 响应
    	response.setContentType("application/json;charset=utf-8");
    	if(jwtTokenUtils.getToken(request) == null) {
    		// 未登录
    		response.getOutputStream().write(new Gson().toJson(RestResult.error(StatusCode.Unauthorized)).getBytes());
    	}else {
    		// 无权限
    		response.getOutputStream().write(new Gson().toJson(RestResult.error(StatusCode.Forbidden)).getBytes());
    	}
    }
}
