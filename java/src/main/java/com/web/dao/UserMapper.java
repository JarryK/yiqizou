package com.web.dao;

import com.web.model.User;
import tk.mybatis.mapper.common.Mapper;


@org.apache.ibatis.annotations.Mapper
public interface UserInfoMapper extends Mapper<User> {
    /**
     * 保存登录信息
     * @param user
     * @return
     */
    public int save(User user);

}
