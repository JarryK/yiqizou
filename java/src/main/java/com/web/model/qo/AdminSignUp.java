package com.web.model.qo;

import com.web.model.Admin;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@ApiModel(value = "管理员注册参数",description = "管理员注册参数")
public class AdminSignUp extends Admin {

    @NotBlank(message = "登录code不能为空")
    private String code;

    @NotBlank(message = "姓名不能为空")
    private String name;

    @NotBlank(message = "电话不能为空")
    private String phone;

    @NotBlank(message = "邮箱不能为空")
    private String email;

    @NotBlank(message = "密码不能为空")
    private String password;
}
