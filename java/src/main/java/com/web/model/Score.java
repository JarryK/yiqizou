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
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "score_log")
public class Score {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "流水id")
    private Long id;

    @Column(name = "user_id")
    @ApiModelProperty(value = "用户id")
    private long userId;

    @Column(name = "user_name")
    @ApiModelProperty(value = "用户名")
    private String userName;

    @Column(name = "alter_score")
    @ApiModelProperty(value = "分数变动")
    private double alterScore;

    @Column(name = "score")
    @ApiModelProperty(value = "改动后分数")
    private double score;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}