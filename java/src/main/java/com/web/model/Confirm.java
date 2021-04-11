package com.web.model;

import com.web.base.TimeModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "sys_order_confirm")
public class Confirm extends TimeModel {
    @Id
    @Column(name = "confirm_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "流水Id")
    private Long confirmId;

    @Column(name = "order_id")
    @ApiModelProperty(value = "订单id")
    private long orderId;

    @Column(name = "user_id")
    @ApiModelProperty(value = "用户ID")
    private long userId;

    @Column(name = "status")
    @ApiModelProperty(value = "确认状态 1=待确认 2=确认")
    private int status;

}
