package com.web.service;

import com.web.model.Admin;
import com.web.security.model.qo.AuthUserQo;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

public interface AdminService {

    public int insert(Admin o,String password) throws Exception;

    public int delete(Admin o);

    public int update(Admin o);

    public Admin selectById(long id);

    public List<Admin> selectAll();

    public Map<String,Object> login(AuthUserQo authUser) throws Exception;

}
