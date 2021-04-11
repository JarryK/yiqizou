package com.web.service.impl;

import com.web.mapper.UserMapper;
import com.web.model.Score;
import com.web.model.User;
import com.web.security.model.UserSecurity;
import com.web.security.service.IUserSecurityService;
import com.web.service.ScoreService;
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
    private final ScoreService scoreService;

    @Override
    public int insert(User o) throws Exception {
        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setPassword("123456");
        securityService.signUp(userSecurity);
        o.setUpDataTime(new Date());
        o.setCreateTime(new Date());
        o.setRegisterStatus(1);
        o.setUserId(Long.valueOf(userSecurity.getUsername()));
        return mapper.insert(o);
    }

    @Override
    public int insert(User o,String password) throws Exception {
        UserSecurity userSecurity = new UserSecurity();
        userSecurity.setPassword(password);
        securityService.signUp(userSecurity);
        o.setUpDataTime(new Date());
        o.setCreateTime(new Date());
        o.setRegisterStatus(1);
        o.setUserId(Long.valueOf(userSecurity.getUsername()));
        return mapper.insert(o);
    }

    @Override
    public int delete(User o) {
        return mapper.deleteByPrimaryKey(o);
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

    @Override
    public void creditScoreBonus(long id,double score) throws Exception{
        // 一天加分不能超过五分
        int i = scoreService.selectOneDayChangesByUId(id);
        if (i > 5){
            return;
        }
        User user = this.selectById(id);
        double newScore = user.getCreditScore() + score;
        if (newScore >= 110){
            newScore = 110;
        }
        user.setCreditScore(newScore);
        Score score1 = new Score();
        score1.setAlterScore(score);
        score1.setUserId(user.getUserId());
        score1.setUserName(user.getNickName());
        score1.setScore(user.getCreditScore());
        scoreService.insert(score1);
        this.update(user);
    }

    @Override
    public void creditScoreDeduction(long id,double score) throws Exception {
        User user = this.selectById(id);
        double newScore = user.getCreditScore() - score;
        if (newScore <= 0){
            newScore = 0;
        }
        user.setCreditScore(newScore);
        Score score1 = new Score();
        score1.setAlterScore(-score);
        score1.setUserId(user.getUserId());
        score1.setUserName(user.getNickName());
        score1.setScore(user.getCreditScore());
        scoreService.insert(score1);
        this.update(user);
    }
}
