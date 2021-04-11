package com.web.service;

import com.web.model.Score;

import java.util.List;


public interface ScoreService {

    public int insert(Score o) throws Exception;

    public int delete(Score o);

    public int update(Score o);

    public Score selectById(long id);

    public List<Score> selectAll();

    /**
     * 查询用户信用分记录
     * @param id
     * @return
     */
    public List<Score> selectByUId(long id);

    /**
     * 查询用户一天信用分增加情况
     * @param id
     * @return
     */
    public int selectOneDayChangesByUId(long id);
}
