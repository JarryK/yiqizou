package com.web.service.impl;

import com.web.mapper.ScoreMapper;
import com.web.model.Score;
import com.web.service.ScoreService;
import com.web.util.DateUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Primary
public class ScoreServiceImpl implements ScoreService {

    private final ScoreMapper mapper;
    @Override
    public int insert(Score o) throws Exception {
        o.setCreateTime(new Date());
        return mapper.insert(o);
    }

    @Override
    public int delete(Score o) {
        return mapper.deleteByPrimaryKey(o);
    }

    @Override
    public int update(Score o) {
        return mapper.updateByPrimaryKey(o);
    }

    @Override
    public Score selectById(long id) {
        Score o = new Score();
        o.setId(id);
        return mapper.selectByPrimaryKey(o);
    }

    @Override
    public List<Score> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public List<Score> selectByUId(long id) {
        Example example = new Example(Score.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",id);
        return mapper.selectByExample(example);
    }

    @Override
    public int selectOneDayChangesByUId(long id){
        String formatTime = DateUtil.DateToFormatTime(new Date(),"yyyy-MM-dd");
        Example example = new Example(Score.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",id);
        criteria.andEqualTo("createTime",formatTime);
        List<Score> scoreList = mapper.selectByExample(example);
        int all = 0;
        for (Score s : scoreList) {
            if (s.getAlterScore() > 0){
                all += s.getAlterScore();
            }
        }
        return all;
    }
}
