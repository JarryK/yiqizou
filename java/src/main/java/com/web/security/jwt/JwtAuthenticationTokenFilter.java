package com.web.security.jwt;

import cn.hutool.core.util.StrUtil;
import com.web.hotdata.HotDataStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationTokenFilter extends GenericFilterBean {

    private final JwtTokenUtils jwtTokenUtils;
    private final JwtSecurityProperties properties;
    private final HotDataStore hotDataStore;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
    	HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    	String token = resolveToken(httpServletRequest);
    	if (StrUtil.isNotBlank(token)) {
    		String username = jwtTokenUtils.getTokenUserName(token);
    		if(token.equals(hotDataStore.get(username))) {
    		    httpServletRequest.setAttribute("username",username);
    			Authentication authentication = jwtTokenUtils.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // token续期
                hotDataStore.expire(username, properties.getTokenValidityInSeconds());
    		}
    	}
    	filterChain.doFilter(servletRequest, servletResponse);
    }
    
    /**
     * 初步检测Token
     *
     * @param request /
     * @return /
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(properties.getHeader());
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(properties.getTokenStartWith())) {
            // 去掉令牌前缀
            return bearerToken.replace(properties.getTokenStartWith(), "");
        } else {
            log.debug("非法Token：{}", bearerToken);
        }
        return null;
    }
}
