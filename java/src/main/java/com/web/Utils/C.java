package com.web.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * <b>项目名称：</b>yiziqou<br>
 * <b>类所处包：</b>com.web.Utils<br>
 * <b>创建人：</b>DadaYu<br>
 * <b>类描述：</b><br>
 * <b>创建时间：</b>2020/11/23 23:53<br>
 */
public class C {

    /**
     * 与页面交互的数据格式定义
     * 属性	类型	说明	最低版本
     * data	string/Object/Arraybuffer	开发者服务器返回的数据
     * statusCode	number	开发者服务器返回的 HTTP 状态码
     * header	Object	开发者服务器返回的 HTTP Response Header	1.2.0
     * cookies	Array.<string>	开发者服务器返回的 cookies，格式为字符串数组	2.10.0
     * profile	Object	网络请求过程中一些调试信息	2.10.4
     */
    public static class page {

        /**
         * 生成一个map，适用于在controller与页面之间约定返回的JSON-map值
         *
         * @param code 返回code
         * @param msg 返回msg
         * @return 返回给UI页面page的数据
         */
        public static Map<String, Object> returnMap(boolean code,String msg) {
            Map<String, Object> returnMap = new HashMap<String, Object>();
            returnMap.put("success", code); // controller与页面之间约定的字段
            returnMap.put("msg", msg); // controller与页面之间约定的字段
            return returnMap;
        }

        /**
         * 生成一个map，适用于在controller与页面之间约定返回的JSON-map值
         *
         * @param code 返回code
         * @param msg 返回msg
         * @param o  返回data
         * @return 返回给UI页面page的数据
         */
        public static Map<String, Object> returnMap(boolean code,String msg, Object o) {
            Map<String, Object> returnMap = new HashMap<String, Object>();
            returnMap.put("success", code); // controller与页面之间约定的字段
            returnMap.put("msg", msg); // controller与页面之间约定的字段
            returnMap.put("data", o); // controller与页面之间约定的字段
            return returnMap;
        }
    }

}
