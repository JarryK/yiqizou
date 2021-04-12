package com.web.service;



import com.web.model.Notice;

import java.util.List;

public interface NoticeService {
    public int insert(Notice o);

    public int delete(Notice o);

    public int update(Notice o);

    public Notice select(long id);

    public List<Notice> selectAll();
}
