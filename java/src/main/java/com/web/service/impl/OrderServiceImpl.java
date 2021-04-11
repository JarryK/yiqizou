package com.web.service.impl;

import com.web.mapper.OrderMapper;
import com.web.model.Order;
import com.web.service.ConfirmService;
import com.web.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Primary
public class OrderServiceImpl implements OrderService {

    private final OrderMapper mapper;
    private final ConfirmService confirmService;
    @Override
    public int insert(Order o) throws Exception {
        o.setUpDataTime(new Date());
        o.setCreateTime(new Date());
        return mapper.insert(o);
    }

    @Override
    public int delete(Order o) {
        return mapper.deleteByPrimaryKey(o);
    }

    @Override
    public int update(Order o) {
        o.setUpDataTime(new Date());
        return mapper.updateByPrimaryKey(o);
    }

    @Override
    public Order selectById(long id) {
        Order o = new Order();
        o.setOrderId(id);
        return mapper.selectByPrimaryKey(o);
    }

    @Override
    public List<Order> selectByUid(long id){
        Example example = new Example(Order.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("createManId",id);
        return mapper.selectByExample(example);
    }

    @Override
    public List<Order> selectAll() {
        return mapper.selectAll();
    }

    @Override
    public List<Map<String, Object>> selectOrderUserListByOId(long id) {
        return mapper.selectOrderUserListByOId(id);
    }
}
