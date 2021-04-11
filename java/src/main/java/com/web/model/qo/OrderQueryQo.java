package com.web.model.qo;

import com.web.model.Order;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;

@Getter
@Setter
@Entity
@ApiModel(value = "订单查询参数",description = "订单查询参数")
public class OrderQueryQo extends Order {
    private int minNum;
    private int maxNum;
    private int minCheckNum;
    private int maxCheckNum;
    private String minTime;
    private String maxTime;
}
