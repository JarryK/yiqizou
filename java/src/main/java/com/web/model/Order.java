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
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "sys_order")
public class Order extends TimeModel {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "订单Id")
    private Long orderId;

    @Column(name = "start_location")
    @ApiModelProperty(value = "出发地址")
    private String startLocation;

    @Column(name = "end_location")
    @ApiModelProperty(value = "目的地")
    private String endLocation;

    @Column(name = "create_man_id")
    @ApiModelProperty(value = "创建人Id")
    private long createManId;

    @Column(name = "create_man")
    @ApiModelProperty(value = "创建人")
    private String createMan;

    @Column(name = "start_time")
    @ApiModelProperty(value = "出发时间")
    private Date startTime;

    @Column(name = "people_number")
    @ApiModelProperty(value = "计划人数")
    private int peopleNumber;

    @Column(name = "people_number_check")
    @ApiModelProperty(value = "确认人数")
    private int peopleNumberCheck;

    @Column(name = "record_photo")
    @ApiModelProperty(value = "记录截图")
    private String recordPhoto;

    @Column(name = "remark")
    @ApiModelProperty(value = "备注")
    private String remark;

    @Column(name = "order_status")
    @ApiModelProperty(value = "订单状态 1=拼单中 2=拼单完成 3=订单结束 4=取消")
    private int orderStatus;

    @Column(name = "platform")
    @ApiModelProperty(value = "使用平台")
    private String platform;
}
