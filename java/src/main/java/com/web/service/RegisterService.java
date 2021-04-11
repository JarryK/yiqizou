package com.web.service;

import com.web.model.Register;

import java.util.List;

public interface RegisterService {

    public int insert(Register o) throws Exception;

    public int delete(Register o);

    public int update(Register o);

    public Register selectById(long id);

    public List<Register> selectAll();
}
