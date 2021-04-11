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

/**
 * <b>项目名称：</b>yiziqou<br>
 * <b>类所处包：</b>com.web.Bean<br>
 * <b>创建人：</b><br>
 * <b>类描述：</b>用户实体类<br>
 * <b>创建时间：</b>2020/11/24 22:52<br>
 */

@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "sys_user_info")
public class User extends TimeModel {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "用户id")
    private Long userId;

    @Column(name = "open_id")
    @ApiModelProperty(value = "标识id")
    private String openId;

    @Column(name = "user_nbr")
    @ApiModelProperty(value = "标识id")
    private String userNbr;

    @Column(name = "nick_name")
    @ApiModelProperty(value = "用户名")
    private String nickName;

    @Column(name = "gender")
    @ApiModelProperty(value = "用户id")
    private int gender;

    @Column(name = "language")
    @ApiModelProperty(value = "语言")
    private String language;

    @Column(name = "province")
    @ApiModelProperty(value = "用户id")
    private String province;

    @Column(name = "city")
    @ApiModelProperty(value = "城市")
    private String city;

    @Column(name = "country")
    @ApiModelProperty(value = "国家")
    private String country;

    @Column(name = "avatar_url")
    @ApiModelProperty(value = "头像")
    private String avatarUrl;

    @Column(name = "phone")
    @ApiModelProperty(value = "电话")
    private String phone;

    @Column(name = "credit_score")
    @ApiModelProperty(value = "信用分")
    private double creditScore;

    @Column(name = "register_id")
    @ApiModelProperty(value = "认证Id")
    private long registerId;

    @Column(name = "register_status")
    @ApiModelProperty(value = "认证状态")
    private int registerStatus;

    @Column(name = "last_login_time")
    @ApiModelProperty(value = "最后登录时间")
    private Date lastLoginTime;



}
