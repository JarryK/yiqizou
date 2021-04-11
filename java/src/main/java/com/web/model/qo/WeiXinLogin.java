package com.web.model.qo;

import com.web.base.TimeModel;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
@ApiModel(value = "微信登录参数",description = "微信登录参数")
public class WeiXinLogin extends TimeModel {

    @NotBlank(message = "微信令牌不能为空")
    private String code;

    @NotBlank(message = "用户信息不能为空")
    private Object userInfo;
}
