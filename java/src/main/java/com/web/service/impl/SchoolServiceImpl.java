package com.web.service.impl;

import com.web.mapper.SchoolMapper;
import com.web.model.School;
import com.web.service.SchoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Primary
public class SchoolServiceImpl implements SchoolService {

    private final SchoolMapper mapper;

    @Override
    public int insert(School o) throws Exception {
        o.setCreateTime(new Date());
        o.setUpDataTime(new Date());
        return mapper.insert(o);
    }

    @Override
    public int delete(School o) {
        return mapper.deleteByPrimaryKey(o);
    }

    @Override
    public int update(School o) {
        return mapper.updateByPrimaryKey(o);
    }

    @Override
    public School selectById(long id) {
        School o = new School();
        o.setSchoolId(id);
        return mapper.selectByPrimaryKey(o);
    }

    @Override
    public List<School> selectAll() {
        return mapper.selectAll();
    }
}
