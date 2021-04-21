package com.androidx.reduce.tools;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class Transition {

    public static final String SECOND = "ss";
    public static final String MINUTE = "mm:ss";
    public static final String HOUR = "HH:mm:ss";
    public static final String DATE_SPRIT = "yyyy/MM/dd";
    public static final String DATE_WHIPPLETREE = "yyyy-MM-dd";
    public static final String DATE_TIME1 = "MM/dd HH:mm:ss";
    public static final String DATE_TIME2 = "MM-dd HH:mm:ss";
    public static final String DATE_TIME3 = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME4 = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_TIME5 = "yyyyMMdd HHmm";
    public static final String DATE_TIME6 = "yyyy-MM-dd HH:mm";
    public static final String DATE_TIME7 = "yyyy年MM月dd日 HH:mm:ss";
    public static final String DATE_TIME8 = "yyyy年MM月dd日";

    /**
     * string类型转换为date类型
     */
    @SuppressLint("SimpleDateFormat")
    public Date strToDate(String strTime, String formatType) {
        SimpleDateFormat formatter = new SimpleDateFormat(formatType);
        try {
            return formatter.parse(strTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 时间转换字符串
     */
    @SuppressLint("SimpleDateFormat")
    private String dateToString(Date date, String formatType) {
        return new SimpleDateFormat(formatType).format(date);
    }

    /**
     * 将String类型的日期时间格式转换成没有（.0）的String日期时间格式
     */
    public String dateToStr(String data, String formatType) {
        return dateToString(strToDate(data, formatType), formatType);
    }

    @SuppressLint("SimpleDateFormat")
    public String stampToDate(String s, String format) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        //如果它本来就是long类型的,则不用写这一步
        long lt = Long.parseLong(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 整数转换保留二位小数点
     */
    @SuppressLint("DefaultLocale")
    public <T> String toDecimal(T v) {
        String sun = String.valueOf(v);
        Float f = Float.valueOf(sun);
        return String.format("%.2f", f);
    }
}
