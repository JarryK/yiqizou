package com.web.base;


/**
 * 调用外部接口地址配置
 */
public class CommonParams {

    public static final class WX_URL {

        public static String appId = "wxd23de144c975f716";

        public static String appSecret = "5b8eacc595035d26740b65645379302a";

        /**
         *   获取 openid和unionid
         *   示例 https://api.weixin.qq.com/sns/jscode2session?appid=APPID&secret=SECRET&js_code=JSCODE&grant_type=authorization_code
         */
        public static  String jscode2session = "https://api.weixin.qq.com/sns/jscode2session";


        /**
         * 获取用户信息的url
         */
        public static  String userInfo = "https://api.weixin.qq.com/sns/userinfo?openid=";
    }
}
