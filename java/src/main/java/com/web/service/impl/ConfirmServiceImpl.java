package com.web.service.impl;

import com.web.mapper.ConfirmMapper;
import com.web.model.Confirm;
import com.web.security.service.IUserSecurityService;
import com.web.service.ConfirmService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Primary
public class ConfirmServiceImpl implements ConfirmService { private final IUserSecurityService securityService;

    private final ConfirmMapper mapper;
    @Override
    public int insert(Confirm o) throws Exception {
        o.setUpDataTime(new Date());
        o.setCreateTime(new Date());
        return mapper.insert(o);
    }

    @Override
    public int delete(Confirm o) {
        return mapper.deleteByPrimaryKey(o);
    }

    @Override
    public int deleteByOrderId(long id){
        Example example = new Example(Confirm.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId",id);
        return mapper.deleteByExample(example);
    }

    @Override
    public int update(Confirm o) {
        o.setUpDataTime(new Date());
        return mapper.updateByPrimaryKey(o);
    }

    @Override
    public List<Confirm> selectByOrderId(long id) {
        Example example = new Example(Confirm.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId",id);
        return mapper.selectByExample(example);
    }

    @Override
    public int selectCountByOrderId(long id){
        Example example = new Example(Confirm.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("orderId",id);
        return mapper.selectCountByExample(example);
    }

    @Override
    public Confirm selectById(long id) {
        Confirm o = new Confirm();
        o.setUserId(id);
        return mapper.selectByPrimaryKey(o);
    }

    @Override
    public List<Confirm> selectByUid(long id){
        Example example = new Example(Confirm.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",id);
        return mapper.selectByExample(example);
    }
    @Override
    public List<Confirm> selectAll() {
        return mapper.selectAll();
    }

}
