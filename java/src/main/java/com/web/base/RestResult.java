package com.web.base;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class RestResult<T> {
	
	@ApiModelProperty(value = "状态码")
	private int code;
	@ApiModelProperty(value = "消息")
	private String message;
	@ApiModelProperty(value = "数据")
	private T data;

	private RestResult(int code, String message, T data){
	  this.code = code;
	  this.message = message;
	  this.data = data;
	}

	public int getCode(){
	  return this.code;
	}
	
	public String getMessage(){
	  return this.message;
	}

	public T getData(){
	  return (T)this.data;
	}
	
	public static <T> RestResult<T> createByStatusCode(StatusCode statusCode){
	  return new RestResult<T>(statusCode.getCode(),statusCode.getMessage(),null);
	}
	
	public static <T> RestResult<T> success(){
	  return new RestResult<T>(StatusCode.SUCCESS.getCode(),StatusCode.SUCCESS.getMessage(),null);
	}
	
	public static <T> RestResult<T> success(String message,T data){
	  return new RestResult<T>(StatusCode.SUCCESS.getCode(),message,data);
	}
	
	public static <T> RestResult<T> success(String message){
	  return new RestResult<T>(StatusCode.SUCCESS.getCode(),message,null);
	}
	
	public static <T> RestResult<T> success(T data){
	  return new RestResult<T>(StatusCode.SUCCESS.getCode(),StatusCode.SUCCESS.getMessage(),data);
	}
	
	public static <T> RestResult<T> error(){
	  return new RestResult<T>(StatusCode.ERROR.getCode(),StatusCode.ERROR.getMessage(),null);
	}
	
	public static <T> RestResult<T> error(StatusCode statusCode){
	  return new RestResult<T>(statusCode.getCode(),statusCode.getMessage(),null);
	}
	
	public static <T> RestResult<T> error(String message){
	  return new RestResult<T>(StatusCode.ERROR.getCode(),message,null);
	}

}
