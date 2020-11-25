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
         * @param success 返回成功/失败
         * @param msg 返回msg
         * @return 返回给UI页面page的数据
         */
        public static Map<String, Object> returnMap(boolean success,String msg) {
            Map<String, Object> returnMap = new HashMap<String, Object>();
            returnMap.put("success", success); // controller与页面之间约定的字段
            returnMap.put("msg", msg); // controller与页面之间约定的字段
            return returnMap;
        }

        /**
         * 生成一个map，适用于在controller与页面之间约定返回的JSON-map值
         *
         * @param success 返回成功/失败
         * @param msg 返回msg
         * @param o  返回data
         * @return 返回给UI页面page的数据
         */
        public static Map<String, Object> returnMap(boolean success,String msg, Object o) {
            Map<String, Object> returnMap = new HashMap<String, Object>();
            returnMap.put("success", success); // controller与页面之间约定的字段
            returnMap.put("msg", msg); // controller与页面之间约定的字段
            returnMap.put("data", o); // controller与页面之间约定的字段
            return returnMap;
        }
    }


    /**
     * 与bmo交互的数据格式定义
     */
    public static class bmo {

        /**
         * 生成一个map，适用于在controller与bmo之间约定返回的JSON-map值
         *
         * @param code 返回code
         * @param msg  返回msg
         * @return
         */
        public static Map<String, Object> returnMap(int code, String msg) {
            Map<String, Object> returnMap = new HashMap<>();
            returnMap.put("success", code);
            returnMap.put("msg", msg);
            return returnMap;
        }

        /**
         * 生成一个map，适用于在controller与bmo之间约定返回的JSON-map值
         *
         * @param success 返回成功/失败
         * @param msg  返回msg
         * @return
         */
        public static Map<String, Object> returnMap(boolean success, String msg) {
            return returnMap(success ? 0 : 1, msg);
        }

        /**
         * 获取返回的map中的success【结果编码状态】
         *
         * @param inMap
         * @return
         */
        public static String returnMapSuccess(Map<String,Object> inMap){
            Object o = MyUtils.map.getObject(inMap,"success","");
            o = o == null ? "" : o;
            String code = o instanceof String ? (String) o : String.valueOf(o);
            return code;
        }
        /**
         * 识别返回的map是否成功
         *
         * @param inMap
         * @return
         */
        public static boolean returnMapBoolean(Map<String,Object> inMap){
            return "0".equals(returnMapSuccess(inMap));
        }


    }

}
