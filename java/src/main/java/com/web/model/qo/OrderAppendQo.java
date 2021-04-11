package com.web.model.qo;

import com.web.model.Order;
import io.swagger.annotations.ApiModel;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@Entity
@ApiModel(value = "新增订单参数",description = "新增订单参数")
public class OrderAppendQo extends Order {

    @NotBlank(message = "出发地址不能为空")
    private String startLocation;

    @NotBlank(message = "目的地不能为空")
    private String endLocation;

    @NotNull(message = "创建人Id不能为空")
    private long createManId;

    @NotNull(message = "创建人不能为空")
    private String createMan;

    @NotBlank(message = "出发时间不能为空 yyyy-MM-dd HH:mm:ss")
    private String time;

    @NotNull(message = "计划人数不能为空")
    private int peopleNumber;

    private String remark;
}
