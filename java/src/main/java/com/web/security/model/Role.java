package com.web.security.model;

import com.web.base.BaseModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "sys_role")
public class Role extends BaseModel {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "ID", hidden = true)
	private Long id;
	
	@ApiModelProperty(value = "名称")
	private String name;
	
	@ApiModelProperty(value = "状态")
	private Boolean status;
	
	@ApiModelProperty(value = "描述")
	private String description;
}
