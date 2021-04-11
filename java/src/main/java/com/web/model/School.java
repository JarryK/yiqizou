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
@Table(name = "school")
public class School extends TimeModel {
    @Id
    @Column(name = "school_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ApiModelProperty(value = "学校id")
    private Long schoolId;

    @Column(name = "school_name")
    @ApiModelProperty(value = "学校名")
    private String schoolName;
}
