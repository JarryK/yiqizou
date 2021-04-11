package com.web.service;


import com.web.model.Order;

import java.util.List;
import java.util.Map;

public interface OrderService {

    public int insert(Order o) throws Exception;

    public int delete(Order o);

    public int update(Order o);

    public Order selectById(long id);

    public List<Order> selectByUid(long id);

    public List<Order> selectAll();

    public List<Map<String,Object>> selectOrderUserListByOId(long id);
}
