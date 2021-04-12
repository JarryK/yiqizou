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
@Table(name = "notice")
public class Notice extends TimeModel {
    @Id
    @Column(name = "NOTICE_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "公告ID")
    private long noticeId;

    @Column(name = "NOTICE_TITLE")
    @ApiModelProperty(value = "公告标题")
    private String noticeTitle;


    @Column(name = "NOTICE_DETAIL")
    @ApiModelProperty(value = "公告内容")
    private String noticeDetail;


    @Column(name = "NOTICE_PUBLISHER")
    @ApiModelProperty(value = "发布人")
    private String noticePublisher;


    @Column(name = "NOTICE_PUBLISHER_ID")
    @ApiModelProperty(value = "发布人Id")
    private Long noticePublisherId;

}