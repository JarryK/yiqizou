package com.web.file.model.qo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@ApiModel(value="用户文件参数类")
public class UserFileQo {

	@NotNull
	@ApiModelProperty(value = "文件名ID，非空",example = "123")
    private Long id;
}
