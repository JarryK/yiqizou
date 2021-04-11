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
@Table(name = "sys_admin")
public class Admin extends TimeModel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "用户id")
    private Long id;

    @Column(name = "code")
    @ApiModelProperty(value = "登录工号")
    private String code;

    @Column(name = "name")
    @ApiModelProperty(value = "姓名")
    private String name;

    @Column(name = "phone")
    @ApiModelProperty(value = "电话")
    private String phone;

    @Column(name = "email")
    @ApiModelProperty(value = "邮箱")
    private String email;
}
