package com.web.dao;

import com.web.model.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.Mapper;


@org.apache.ibatis.annotations.Mapper
public interface UserMapper extends Mapper<User> {

    @Select("select * from sys_user_info where open_id = #{openid}")
    public User selectByOpenId(@Param("openid") String openid);
}
