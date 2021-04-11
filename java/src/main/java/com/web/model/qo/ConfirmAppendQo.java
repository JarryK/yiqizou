package com.web.model.qo;

import com.web.model.Confirm;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@ApiModel(value = "新增订单确认信息参数",description = "新增订单确认信息参数")
public class ConfirmAppendQo extends Confirm {

    @NotNull(message = "订单id不能为空")
    private long orderId;

    @NotNull(message = "用户id不能为空")
    private long userId;
}
