package com.web.model;

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
@Table(name = "order_confirm")
public class Confirm extends TimeModel{
    @Id
    @Column(name = "confirm_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "流水Id")
    private long confirmId;

    @Column(name = "order_id")
    @ApiModelProperty(value = "订单id")
    private long orderId;

    @Column(name = "user_id")
    @ApiModelProperty(value = "用户ID")
    private long userId;

}
