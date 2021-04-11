package com.web.security.config;

import com.web.hotdata.HotDataStore;
import com.web.security.custom.JechoAuthenticationEntryPoint;
import com.web.security.custom.JechoPasswordEncoder;
import com.web.security.jwt.JwtSecurityProperties;
import com.web.security.jwt.JwtTokenUtils;
import com.web.security.jwt.TokenConfigurer;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;


/**
 * 使用SpringSecurity + JWT的方式做权限处理 
 * 主要是判断请求头Header中的Token信息
 * */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, proxyTargetClass = true) //启用方法级的权限认证
@RequiredArgsConstructor
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {
	
    // Token 工具
    private final JwtTokenUtils jwtTokenUtils;
    // Token 配置
	private final JwtSecurityProperties properties;
	
	private final HotDataStore hotDataStore;
	
	// 认证 处理器
    private final JechoAuthenticationEntryPoint jechoAuthenticationEntryPoint;
	
	/**
	 * 设置SpringSecurity权限前缀，默认为 ROLE_
	 * */
	@Bean
    GrantedAuthorityDefaults grantedAuthorityDefaults() {
        // 去除 ROLE_ 前缀
        return new GrantedAuthorityDefaults("");
    }
	
	/**
	 * 配置用户密码的加密方式
	 * */
	@Bean
    PasswordEncoder passwordEncoder() {
		return new JechoPasswordEncoder();
    }
	
    /**
     *   获取自定义JWT filter
     * */
    private TokenConfigurer securityConfigurerAdapter() {
        return new TokenConfigurer(jwtTokenUtils, properties,hotDataStore);
    }
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
    	httpSecurity
		        .csrf().disable()	// 禁用 CSRF
		        .exceptionHandling()	// 异常处理
		        .authenticationEntryPoint(jechoAuthenticationEntryPoint)
		        // 防止iframe 造成跨域
		        .and() 
		        .headers().frameOptions().disable()
		        // 不创建会话
		        .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 授权请求
                .and()
    			.authorizeRequests()
    			// 放行静态资源
                .antMatchers(
                        HttpMethod.GET,
                        "/*.html",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/webSocket/**",
                        "/static/**"
                ).permitAll()
                // swagger 文档
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/*/api-docs").permitAll()
                // 文件
                .antMatchers("/avatar/**").permitAll()
                .antMatchers("/file/**").permitAll()
                // 阿里巴巴 druid
                .antMatchers("/druid/**").permitAll()
                // 放行OPTIONS请求
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                //允许匿名及登录用户访问
                .antMatchers(
                		"/yqz/**/**",
                		"/error/**",
                		"/test/**",
                		"/**/signUp/**",
						"/**/signIn/**",
						"/**/wxLogin/**",
						"/**/qryOne/**",
						"/**/qry/**"
                ).permitAll()
                // 所有请求都需要认证
                .anyRequest().authenticated()
                // *应用我们自定义的JWT filter
                .and()
                .apply(securityConfigurerAdapter())
                ;
    }
    
}
