package com.web.service.impl;

import com.web.mapper.NoticeMapper;
import com.web.model.Notice;
import com.web.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Primary
public class NoticeServiceImpl implements NoticeService {

    private final NoticeMapper mapper;
    @Override
    public int insert(Notice o) {
        o.setCreateTime(new Date());
        o.setUpDataTime(new Date());
        return mapper.insert(o);
    }

    @Override
    public int delete(Notice o) {
        return mapper.deleteByPrimaryKey(o);
    }

    @Override
    public int update(Notice o) {
        o.setUpDataTime(new Date());
        return mapper.updateByPrimaryKey(o);
    }

    @Override
    public Notice select(long id) {
        Notice m = new Notice();
        m.setNoticeId(id);
        return mapper.selectByPrimaryKey(m);
    }

    @Override
    public List<Notice> selectAll() {
        return mapper.selectAll();
    }
}
