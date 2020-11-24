package com.web.Utils;

import java.util.Map;

/**
 * <b>项目名称：</b>yiziqou<br>
 * <b>类所处包：</b>java.com.web.Utils.<br>
 * <b>创建人：</b><br>
 * <b>类描述：</b>Map获取工具类<br>
 * <b>创建时间：</b>2020/11/23 23:07<br>
 */
public class MapTool {

    /**
     * 获得Map指定key的value  String类型返回
     *
     * @param map
     * @param key
     * @return
     */
    public static String getString(Map map, Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null) {
                return answer.toString();
            }
        }
        return null;
    }

    /**
     * 获得Map中的Map
     *
     * @param map
     * @param key
     * @return
     */
    public static Map getMap(Map map, Object key) {
        if (map != null) {
            Object answer = map.get(key);
            if (answer != null && answer instanceof Map) {
                return (Map) answer;
            }
        }
        return null;
    }

    /**
     * 获得Map指定key的value  Object类型返回
     * @param map
     * @param key
     * @return
     */
    public static Object getObject(Map map, Object key) {
        return map != null ? map.get(key) : null;
    }

}
