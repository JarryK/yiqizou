package com.web.security.model;

import com.web.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import tk.mybatis.mapper.annotation.NameStyle;
import tk.mybatis.mapper.code.Style;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "sys_user_role")
@NameStyle(Style.camelhumpAndLowercase)
public class UserRole extends BaseModel{

	@ApiModelProperty(value = "用户id")
	private Long userId;
	
	@ApiModelProperty(value = "角色id")
	private Long roleId;
}
