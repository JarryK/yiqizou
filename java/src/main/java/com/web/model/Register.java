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
@Table(name = "register")
public class Register extends TimeModel {
    @Id
    @Column(name = "register_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "流水Id")
    private Long registerId;

    @Column(name = "user_id")
    @ApiModelProperty(value = "用户Id")
    private long userId;

    @Column(name = "register_photo1")
    @ApiModelProperty(value = "认证照片1")
    private String registerPhoto1;

    @Column(name = "register_photo3")
    @ApiModelProperty(value = "认证照片2")
    private String registerPhoto2;

    @Column(name = "register_photo3")
    @ApiModelProperty(value = "认证照片3")
    private String registerPhoto3;

    @Column(name = "register_status")
    @ApiModelProperty(value = "认证状态 1=待认证 2=通过 3=未通过")
    private int registerStatus;

    @Column(name = "register_man_id")
    @ApiModelProperty(value = "审批人Id")
    private long registerManId;

    @Column(name = "register_man")
    @ApiModelProperty(value = "审批人")
    private String registerMan;
}
