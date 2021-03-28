package com.web.service;

import com.web.bean.User;

public interface LoginService {

    /**
     * 保存用户登录信息
     * @param user
     * @return
     */
    int saveUser(User user) throws Exception;
}
