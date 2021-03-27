package com.web.exception.handler;

import com.web.base.RestResult;
import com.web.base.StatusCode;
import com.web.exception.BadConfigurationException;
import com.web.util.ThrowableUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
	
    /**
     * 处理所有不可知的异常
     */
    @ExceptionHandler(Throwable.class)
    public RestResult<Object> handleException(Throwable e){
        // 打印堆栈信息
        log.error(ThrowableUtil.getStackTrace(e));
        return RestResult.error(e.getMessage());
    }
	
	/**
     * 处理所有接口数据验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResult<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e){
        // 打印堆栈信息
         log.error(ThrowableUtil.getStackTrace(e));
        
        String[] str = Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getCodes())[1].split("\\.");
        String errorMessage = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        String message = str[1] + ":" + errorMessage;
        return RestResult.error(message);
    }
    
    /**
     * 错误配置信息 异常异常
     */
    @ExceptionHandler(value = BadConfigurationException.class)
	public RestResult<Object> badRequestException(BadConfigurationException e) {
	  // 打印堆栈信息
	  log.error(ThrowableUtil.getStackTrace(e));
	  return RestResult.error(e.getMessage());
	}
    
    /**
     * 无权限异常
     * */
    @ExceptionHandler(value = AccessDeniedException.class)
    public RestResult<Object> accessDenied(AccessDeniedException e) {
  	  // 打印堆栈信息
  	  //log.error(ThrowableUtil.getStackTrace(e));
  	  return RestResult.error(StatusCode.Forbidden);
  	}

}
