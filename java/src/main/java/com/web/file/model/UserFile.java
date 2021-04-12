package com.web.file.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.web.base.TimeModel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "sys_user_file")
@NoArgsConstructor
@ApiModel(value="用户文件类")
public class UserFile extends TimeModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@ApiModelProperty(value = "ID", hidden = true)
	private Long id;
	
	@ApiModelProperty(value = "文件名（上传时）",example = "example.png")
	private String fileName;
	
	@ApiModelProperty(value = "文件名（本地文件名）",example = "examplexxx-xxx-xxx-xxx.png")
	private String realName;
	
	@JsonIgnore
	@ApiModelProperty(value = "文件路径",example = "C:\\xxx\\example.png")
	private String filePath;
	
	@ApiModelProperty(value = "文件大小/kb",example = "1024")
	private Long size;
	
	@ApiModelProperty(value = "是否为图片文件",example = "true")
	private Boolean isImg;

}
