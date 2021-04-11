package com.web.service;

import com.web.model.User;

import java.util.List;

/**
 * <b>项目名称：</b>yiziqou<br>
 * <b>类所处包：</b>com.web.service<br>
 * <b>创建人：</b>DadaYu<br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2021/4/7 22:35<br>
 */
public interface UserService {

    public int insert(User o) throws Exception;

    public int delete(User o);

    public int update(User o);

    public User selectById(long id);

    public User selectByOpenId(String id);

    public List<User> selectAll();

    /**
     * 用户信用分增加
     */
    public void creditScoreBonus(long id,double score) throws Exception;

    /**
     * 用户信用分减少
     */
    public void creditScoreDeduction(long id,double score) throws Exception;

}
