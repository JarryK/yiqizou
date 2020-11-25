package com.web.Utils;

import java.util.Date;
import java.util.Map;
import java.util.Random;

/**
 * <b>项目名称：</b>yiziqou<br>
 * <b>类所处包：</b>java.com.web.Utils.<br>
 * <b>创建人：</b><br>
 * <b>类描述：</b>工具类<br>
 * <b>创建时间：</b>2020/11/23 23:07<br>
 */
public class MyUtils {

    /**
     * map相关工具
     */
    public static class map {

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
         * 获得Map指定key的value  Boolean类型返回
         *
         * @param map
         * @param key
         * @return
         */
        public static Boolean getBoolean(final Map map, final Object key) {
            if (map != null) {
                Object answer = map.get(key);
                if (answer != null) {
                    if (answer instanceof Boolean) {
                        return (Boolean) answer;

                    } else if (answer instanceof String) {
                        return new Boolean((String) answer);

                    } else if (answer instanceof Number) {
                        Number n = (Number) answer;
                        return (n.intValue() != 0) ? Boolean.TRUE : Boolean.FALSE;
                    }
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
        public static Object getObject(final Map map, final Object key) {
            if (map != null) {
                return map.get(key);
            }
            return null;
        }

        /**
         * 获得Map指定key的value  String类型返回 有默认值
         *
         * @param map
         * @param key
         * @return
         */
        public static String getString( Map map, Object key, String defaultValue ) {
            String answer = getString( map, key );
            if ( answer == null ) {
                answer = defaultValue;
            }
            return answer;
        }

        /**
         * 获得Map指定key的value  Boolean型返回 有默认值
         *
         * @param map
         * @param key
         * @return
         */
        public static Boolean getBoolean( Map map, Object key, Boolean defaultValue ) {
            Boolean answer = getBoolean( map, key );
            if ( answer == null ) {
                answer = defaultValue;
            }
            return answer;
        }

        /**
         * 获取Map中的Map 有默认值
         *
         * @param map
         * @param key
         * @return
         */
        public static Map getMap( Map map, Object key, Map defaultValue ) {
            Map answer = getMap( map, key );
            if ( answer == null ) {
                answer = defaultValue;
            }
            return answer;
        }

        /**
         * 获得Map指定key的value  getObject类型返回 有默认值
         *
         * @param map
         * @param key
         * @return
         */
        public static Object getObject( Map map, Object key, Object defaultValue ) {
            if ( map != null ) {
                Object answer = map.get( key );
                if ( answer != null ) {
                    return answer;
                }
            }
            return defaultValue;
        }
    }

    /**
     * . 生成随机码
     *
     * @param m 位数
     * @return String
     */
    public static String getRandomNuber(int m) {
        Random rd = new Random();
        int number = 1;
        for (int i = 0; i < m; i++) {
            number *= 10;
        }
        String string = rd.nextInt(number) + String.valueOf(rd.nextInt(number)) + number;
        return string.substring(0, m);
    }

    /**
     * 生产随机表ID 返回Integer类型
     * @param _length
     * @return
     */
    public static Long getRandomTableId(int _length){
        String s = String.valueOf(new Date().getTime());
        s = s.substring(0,5);
        return Long.valueOf(s + getRandomNuber(_length - 6));
    }
}
