package com.web.base;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "wx")
public class WeiXinParams {

    /**
     * 小程序appId
     */
    public String appId;

    /**
     * 小程序Secret
     */
    public String appSecret;

    /**
     *   获取 openid和unionid接口地址
     *   示例 https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
     */
    public String jscode2session;


    /**
     * 获取用户信息的接口地址
     */
    public String userInfo;

}
