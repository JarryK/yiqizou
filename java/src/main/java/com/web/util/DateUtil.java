package com.web.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    /**
     * Date转换格式化时间
     * @param currentTime 传入的时间
     * @return 格式化时间
     * @throws ParseException
     */
    public static String DateToFormatTime(Date currentTime) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return formatter.format(currentTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * Date转换格式化时间
     * @param currentTime 传入的时间
     * @return 格式化时间
     * @throws ParseException
     */
    public static String DateToFormatTime(Date currentTime,String format) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            return formatter.format(currentTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 时间戳转换成格式化时间
     * @param s 传入的时间戳
     * @return 返回格式化时间
     */
    public static String timeStampToTime(String s){
        String res = "";
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long lt = new Long(s);
            Date date = new Date(lt);
            res = simpleDateFormat.format(date);
            return res;
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
     * 字符时间转换成时间
     * @param s 传入的时间字符
     * @return 返回格式化时间
     */
    public static Date timeStringToDate(String s){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
            return formatter.parse(s);
        } catch (NumberFormatException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 字符时间转换成时间
     * @param s 传入的时间字符 format 指定格式
     * @return 返回格式化时间
     */
    public static Date timeStringToDate(String s,String format){
        try {
            SimpleDateFormat formatter = new SimpleDateFormat( format);
            return formatter.parse(s);
        } catch (NumberFormatException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 时间戳转换成时间
     * @param s 传入的时间戳
     * @return 返回date
     */
    public static Date timeStampToDate(String s){
        try {
            long lt = new Long(s);
            return new Date(lt);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return null;
    }

}
