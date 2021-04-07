package com.web.model.qo;

import com.web.model.TimeModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Entity
public class WeiXinLogin extends TimeModel {

    @NotBlank(message = "微信令牌不能为空")
    private String code;

    @NotBlank(message = "用户信息不能为空")
    private Object userInfo;
}
