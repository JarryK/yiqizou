package com.web.service.impl;

import com.web.bean.User;
import com.web.dao.UserInfoMapper;
import com.web.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@RequiredArgsConstructor
@Primary
public class LoginServiceImpl implements LoginService {

    /**
     * 日志输出.
     */
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private final UserInfoMapper loginDao;

    @Override
    public int saveUser(User user) throws Exception {

        return  loginDao.insert(user);
    }
}
