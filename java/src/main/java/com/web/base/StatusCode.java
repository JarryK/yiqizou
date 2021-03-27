package com.web.base;

public enum StatusCode {
    SUCCESS(200, "成功"),
    NODATA(201, "返回数据为空"),
    
    Unauthorized(401,"未登录"),
    Forbidden(403, "无权限"),
	NOT_URL_ERROR(404, "无访问资源"),
    
    ERROR(500, "失败"),
    DATA_READ_ERROR(510, "读取request数据发生io错误"),
    DATA_NULL_ERROR(511, "获取数据不能为空"),
    NOT_JSON_FORMAT_ERROR(512, "不是有效json数据");
    
    private final int code;
    private final String message;

    private StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
    
}
