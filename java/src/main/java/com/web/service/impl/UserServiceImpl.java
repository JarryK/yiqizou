package com.web.service.impl;

import com.web.dao.UserMapper;
import com.web.model.User;
import com.web.security.model.UserSecurity;
import com.web.security.service.IUserSecurityService;
import com.web.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Primary
public class UserServiceImpl implements UserService {

    private final IUserSecurityService securityService;
    private final UserMapper mapper;

    @Override
    public int insert(User o) throws Exception {
        o.setUpDataTime(new Date());
        o.setCreateTime(new Date());
        mapper.insert(o);
        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setUsername(o.getUserId());
        userSecurity.setPassword("123456");
        return securityService.signUp(userSecurity);
    }

    @Override
    public int delete(User o) {
        return mapper.delete(o);
    }

    @Override
    public int update(User o) {
        o.setUpDataTime(new Date());
        return mapper.updateByPrimaryKey(o);
    }

    @Override
    public User selectById(long id) {
        User o = new User();
        o.setUserId(id);
        return mapper.selectByPrimaryKey(o);
    }

    @Override
    public User selectByOpenId(String id) {
        return mapper.selectByOpenId(id);
    }

    @Override
    public List<User> selectAll() {
        return mapper.selectAll();
    }
}
