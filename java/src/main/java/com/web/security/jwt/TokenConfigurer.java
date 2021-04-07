package com.web.security.jwt;

import com.web.hotdata.HotDataStore;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 自定义 JWT Token 配置
 */
@RequiredArgsConstructor
public class TokenConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {

	private final JwtTokenUtils jwtTokenUtils;
	private final JwtSecurityProperties properties;
	private final HotDataStore hotDataStore;

    @Override
    public void configure(HttpSecurity http) {
    	JwtAuthenticationTokenFilter customFilter = new JwtAuthenticationTokenFilter(jwtTokenUtils,properties,hotDataStore);
        http.addFilterBefore(customFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
