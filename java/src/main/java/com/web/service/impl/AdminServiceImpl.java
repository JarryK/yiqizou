package com.web.service.impl;

import com.web.hotdata.HotDataStore;
import com.web.mapper.AdminMapper;
import com.web.model.Admin;
import com.web.model.User;
import com.web.security.jwt.JwtSecurityProperties;
import com.web.security.jwt.JwtTokenUtils;
import com.web.security.mapper.UserRoleMapper;
import com.web.security.model.UserRole;
import com.web.security.model.UserSecurity;
import com.web.security.model.qo.AuthUserQo;
import com.web.security.service.IUserSecurityService;
import com.web.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Primary
public class AdminServiceImpl implements AdminService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenUtils jwtTokenUtils;
    private final JwtSecurityProperties properties;
    private final HotDataStore hotDataStore;

    private final AdminMapper mapper;
    private final IUserSecurityService securityService;
    private final UserRoleMapper userRoleMapper;

    @Override
    public int insert(Admin o,String password) throws Exception {
        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setPassword(password);
        securityService.signUp(userSecurity);
        UserRole userRole = new UserRole();
        userRole.setRoleId(1L);
        userRole.setUserId(userSecurity.getId());
        userRoleMapper.insert(userRole);
        o.setUpDataTime(new Date());
        o.setCreateTime(new Date());
        o.setId(Long.valueOf(userSecurity.getUsername()));
        return mapper.insert(o);
    }

    @Override
    public int delete(Admin o) {
        return mapper.deleteByPrimaryKey(o);
    }

    @Override
    public int update(Admin o) {
        o.setUpDataTime(new Date());
        return mapper.updateByPrimaryKey(o);
    }

    @Override
    public Admin selectById(long id) {
        Admin o = new Admin();
        o.setId(id);
        return mapper.selectByPrimaryKey(o);
    }

    @Override
    public List<Admin> selectAll() {
        return mapper.selectAll();
    }

    @SuppressWarnings("serial")
    @Override
    public Map<String, Object> login(AuthUserQo authUser) throws Exception {
        String password = authUser.getPassword();
        // Rsa密码解密
        // password = RsaUtils.decryptByPrivateKey(RsaProperties.privateKey, authUser.getPassword());
        // 查询验证码
        String code = (String) hotDataStore.get(authUser.getUuid());
        // 清除验证码
        hotDataStore.del(authUser.getUuid());
//		if(StringUtils.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)){
//			throw new Exception("验证码错误");
//		}
        // 密码校验
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authUser.getUsername(), password);
        // 用户名和密码校验
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        // 校验成功将身份信息放入SecurityContextHolder
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 创建Token
        String token = jwtTokenUtils.createToken(authentication);
        Admin admin = this.selectById(Long.parseLong(authentication.getName()));
        hotDataStore.set(authUser.getUsername() + "_info",admin);
        // 将Token信息放入缓存,如果之前有相同账号登录过的话，新的token会覆盖原token，原token不可用，相当于自动踢掉已登录用户
        hotDataStore.set(authUser.getUsername().toString(), token,properties.getTokenValidityInSeconds());
        final UserSecurity userSecurity = (UserSecurity) authentication.getPrincipal();
        Map<String, Object> authInfo = new HashMap<String, Object>(3) {{
            put("token", properties.getTokenStartWith() + token);
            put("admin", userSecurity);
            put("admin_info", admin);
        }};
        return authInfo;
    }

}
