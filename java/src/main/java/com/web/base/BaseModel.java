package com.web.base;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import java.sql.Timestamp;

@Getter
public class BaseModel {

	@JsonIgnore
    @Column(name = "create_by", updatable = false)
    @ApiModelProperty(value = "创建人", hidden = true)
    private String createBy;

	@JsonIgnore
    @Column(name = "update_by")
    @ApiModelProperty(value = "更新人", hidden = true)
    private String updateBy;

	@JsonIgnore
	@Getter
    @Column(name = "create_time", updatable = false)
    @ApiModelProperty(value = "创建时间", hidden = true)
    private Timestamp createTime;

	@JsonIgnore
	@Getter
    @Column(name = "update_time")
    @ApiModelProperty(value = "更新时间", hidden = true)
    private Timestamp updateTime;

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public void setCreateBy(String createBy) {
		this.createTime = new Timestamp(System.currentTimeMillis());
		this.createBy = createBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateTime = new Timestamp(System.currentTimeMillis());
		this.updateBy = updateBy;
	}
	
	public void clearBaseInfo() {
		this.createBy = null;
		this.createTime = null;
		this.updateBy =  null;
		this.updateTime = null;
	}
	

}
