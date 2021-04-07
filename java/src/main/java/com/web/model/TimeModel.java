package com.web.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
@Entity
public class TimeModel {
    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    @Column(name = "upData_time")
    @ApiModelProperty(value = "更新时间")
    private Date upDataTime;
}
