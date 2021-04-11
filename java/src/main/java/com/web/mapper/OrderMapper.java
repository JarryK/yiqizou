package com.web.mapper;

import com.web.model.Order;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;
import java.util.Map;

@org.apache.ibatis.annotations.Mapper
public interface OrderMapper extends Mapper<Order> {

    /**
     * 根据订单id查询订单用户信息
     * @param id
     * @return
     */
    List<Map<String,Object>> selectOrderUserListByOId(long id);
}
