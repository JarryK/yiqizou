package com.web.security.jwt;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.web.security.model.UserSecurity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.ArrayList;
import java.util.List;

/**
 * 工具类实现创建token与校验token功能
 */
@Component
public class JwtTokenUtils implements InitializingBean {

    private final JwtSecurityProperties properties;
    private static final String AUTHORITIES_KEY = "auth";
    private static final String ROLE_SEPARATOR = ",";
    private JwtParser jwtParser;
    private JwtBuilder jwtBuilder;

    public JwtTokenUtils(JwtSecurityProperties jwtSecurityProperties) {
        this.properties = jwtSecurityProperties;
    }

    @Override
    public void afterPropertiesSet() {
    	byte[] keyBytes = Decoders.BASE64.decode(properties.getBase64Secret());
        Key key = Keys.hmacShaKeyFor(keyBytes);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        jwtBuilder = Jwts.builder().signWith(key, SignatureAlgorithm.HS512);
    }


    /**
     * 创建Token
     * */
    public  String createToken (Authentication authentication) {
    	StringBuffer roleStr = new StringBuffer();
    	for(GrantedAuthority a :authentication.getAuthorities()) {
    		roleStr.append(a.getAuthority());
    		roleStr.append(ROLE_SEPARATOR);
    	}
    	return jwtBuilder
                // 加入ID确保生成的 Token 都不一致
                .setId(IdUtil.simpleUUID())
                // 签发日期
                //.setIssuedAt(new Date())
                // 过期时间 (这里不在Token中记录过期时间，由热数据容器维护)
                //.setExpiration(new Date((new Date()).getTime() + properties.getTokenValidityInSeconds()))
                // 压缩
                //.compressWith(CompressionCodecs.DEFLATE)
                // 自定义数据
                .claim(AUTHORITIES_KEY, roleStr.toString())
                // 面向用户
                .setSubject(authentication.getName())
                .compact();
    }

    /**
     * 创建Token
     * */
    public  String createToken (UserSecurity userSecurity) {
    	StringBuffer roleStr = new StringBuffer();
    	for(GrantedAuthority a :userSecurity.getAuthorities()) {
    		roleStr.append(a.getAuthority());
    		roleStr.append(ROLE_SEPARATOR);
    	}
    	return jwtBuilder
                // 加入ID确保生成的 Token 都不一致
                .setId(IdUtil.simpleUUID())
                // 签发日期
                //.setIssuedAt(new Date())
                // 过期时间 (这里不在Token中记录过期时间，由热数据容器维护)
                //.setExpiration(new Date((new Date()).getTime() + properties.getTokenValidityInSeconds()))
                // 压缩
                //.compressWith(CompressionCodecs.DEFLATE)
                // 自定义数据
                .claim(AUTHORITIES_KEY, roleStr.toString())
                // 面向用户
                .setSubject(userSecurity.getUsername())
                .compact();
    }

    /**
     * 依据Token 获取鉴权信息
     *
     * @param token /
     * @return /
     */
    Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);

        List<SimpleGrantedAuthority> auths = new ArrayList<>();
        String roleStr = (String) claims.get(AUTHORITIES_KEY);
        String[] roles = roleStr.split(ROLE_SEPARATOR);
        for (String role:roles) {
        	if(StrUtil.isNotBlank(role)) {
        		auths.add(new SimpleGrantedAuthority(role));
        	}
		}
        
        return new UsernamePasswordAuthenticationToken(claims.getSubject(), token, auths);
    }
    
    public Claims getClaims(String token) {
        return jwtParser
                .parseClaimsJws(token)
                .getBody();
    }
    
    public String getTokenUserName(String token) {
    	Claims claims = getClaims(token);
    	return claims.getSubject();
    }
    
    public String getTokenUserName(HttpServletRequest request) {
    	String token = this.getToken(request);
    	if(token == null || StringUtils.isBlank(token)) {
    		return null;
    	}
    	return getClaims(token).getSubject();
    }
    
    /**
     * 从request的Header中获取Token信息
     * @param request
     * @return Token字符串
     * */
    public String getToken(HttpServletRequest request) {
        final String requestHeader = request.getHeader(properties.getHeader());
        if (requestHeader != null && requestHeader.startsWith(properties.getTokenStartWith())) {
            return requestHeader.substring(7);
        }
        return null;
    }

}