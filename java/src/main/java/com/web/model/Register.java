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
@Table(name = "register")
public class Register extends TimeModel {
    @Id
    @Column(name = "register_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "流水Id")
    private long register_id;

    @Column(name = "user_id")
    @ApiModelProperty(value = "用户Id")
    private long user_id;

    @Column(name = "register_photo1")
    @ApiModelProperty(value = "认证照片1")
    private long registerPhoto1;

    @Column(name = "register_photo3")
    @ApiModelProperty(value = "认证照片2")
    private long registerPhoto2;

    @Column(name = "register_photo3")
    @ApiModelProperty(value = "认证照片3")
    private long registerPhoto3;

    @Column(name = "register_status")
    @ApiModelProperty(value = "认证状态")
    private long registerStatus;

    @Column(name = "register_man_id")
    @ApiModelProperty(value = "审批人Id")
    private long registerManId;

    @Column(name = "register_man")
    @ApiModelProperty(value = "审批人")
    private long registerMan;








}
