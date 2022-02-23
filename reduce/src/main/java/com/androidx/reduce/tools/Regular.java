package com.androidx.reduce.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 正则工具类
 */
@SuppressWarnings({"RedundantSuppression", "unused"})
public final class Regular {

    // 手机号验证 [1][3578]\d{9}
    private static final String PHONE = "^((13[0-9])|(14[5,7]|[9])|(15[0-3,5-9])|(17[0-8])|(18[0-9])|(19[8-9]))\\d{8}$";
    // 汉语正则表达式（android）
    private static final String CHINESE = "^[\\u4E00-\\u9FA5\\uF900-\\uFA2D]+$";
    // 验证邮箱
    private static final String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    // 身份证验证
    private static final String CARD_ID = "(^[1-9]\\d{5}(18|19|20)\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$)|\" + \"(^[1-9]\\d{5}\\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\\d{3}$)";
    // IP验证
    private static final String IP = "(2[5][0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})\\.(25[0-5]|2[0-4]\\d|1\\d{2}|\\d{1,2})";
    private static final String IP1 = "^[1-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}:[0-9]{1,4}$";
    // 域名验证
    private static final String URL = "(^[w]{3}\\.([A-Za-z0-9]+\\.)([a-z]+|[a-z]+\\.[a-z]+)$)";
    // 邮政编码验证
    private static final String POSTCODE = "^\\d{4,6}$";
    // 密码验证
    private static final String PASSWORD = "^[a-zA-Z0-9]{6,16}$";
    // 密码验证
    private static final String BLEND_PASSWORD = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
    // 日期验证
    private static final String DATE = "^[1-9]{4}(-|\\. |/)(([0][1-9])|([1][0-2]))(-|\\. |/)(([0][1-9])|([1][1-9])|([2][1-9])|[3][0-1])$";
    // 时间验证
    private static final String TIME = "^([1-9]:)|([0-1][0-9]:)|([0-2][0-3]:)([0-5][0-9]:)([0-5][0-9])$";
    // 空白字符验证
    private static final String SPACE = "\\s+";
    // https验证
    private static final String HTTPS = "(https?|http|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";

    private Regular() {
    }

    /**
     * 正则·手机号校验
     */
    public static Boolean isMobile(String parameter) {
        // 设定查看模式
        Pattern pattern = Pattern.compile(PHONE);
        // 判断parameter是否匹配，返回匹配结果
        Matcher matcher = pattern.matcher(parameter);
        // 返回结果（true）
        return matcher.matches();
    }

    /**
     * 正则·中文校验
     */
    public static Boolean isChinese(String parameter) {
        // 设定查看模式
        Pattern pattern = Pattern.compile(CHINESE);
        // 判断parameter是否匹配，返回匹配结果
        Matcher matcher = pattern.matcher(parameter);
        // 返回结果（true）
        return matcher.matches();
    }

    /**
     * 正则·邮箱校验
     */
    public static Boolean isEmail(String parameter) {
        // 设定查看模式
        Pattern pattern = Pattern.compile(EMAIL);
        // 判断parameter是否匹配，返回匹配结果
        Matcher matcher = pattern.matcher(parameter);
        // 返回结果（true）
        return matcher.matches();
    }

    /**
     * 正则·身份证校验
     */
    public static Boolean isCardId(String parameter) {
        // 设定查看模式
        Pattern pattern = Pattern.compile(CARD_ID);
        // 判断parameter是否匹配，返回匹配结果
        Matcher matcher = pattern.matcher(parameter);
        // 返回结果（true）
        return matcher.matches();
    }

    /**
     * 正则·IP校验
     */
    public static Boolean isIp(String parameter) {
        // 设定查看模式
        Pattern pattern = Pattern.compile(IP);
        // 判断parameter是否匹配，返回匹配结果
        Matcher matcher = pattern.matcher(parameter);
        // 返回结果（true）
        return matcher.matches();
    }

    /**
     * 正则·IP校验
     */
    public static Boolean isIps(String parameter) {
        // 设定查看模式
        Pattern pattern = Pattern.compile(IP1);
        // 判断parameter是否匹配，返回匹配结果
        Matcher matcher = pattern.matcher(parameter);
        // 返回结果（true）
        return matcher.matches();
    }

    /**
     * 正则·URL校验
     */
    public static Boolean isUrl(String parameter) {
        // 设定查看模式
        Pattern pattern = Pattern.compile(URL);
        // 判断parameter是否匹配，返回匹配结果
        Matcher matcher = pattern.matcher(parameter);
        // 返回结果（true）
        return matcher.matches();
    }

    /**
     * 正则·密码校验1
     */
    public static Boolean isPassword(String parameter) {
        // 设定查看模式
        Pattern pattern = Pattern.compile(PASSWORD);
        // 判断parameter是否匹配，返回匹配结果
        Matcher matcher = pattern.matcher(parameter);
        // 返回结果（true）
        return matcher.matches();
    }

    /**
     * 正则·密码校验2
     */
    public static Boolean isBlendPassword(String parameter) {
        // 设定查看模式
        Pattern pattern = Pattern.compile(BLEND_PASSWORD);
        // 判断parameter是否匹配，返回匹配结果
        Matcher matcher = pattern.matcher(parameter);
        // 返回结果（true）
        return matcher.matches();
    }

    /**
     * 正则·邮政编码校验
     */
    public static Boolean isPostcode(String parameter) {
        // 设定查看模式
        Pattern pattern = Pattern.compile(POSTCODE);
        // 判断parameter是否匹配，返回匹配结果
        Matcher matcher = pattern.matcher(parameter);
        // 返回结果（true）
        return matcher.matches();
    }

    /**
     * 正则·空白字符校验
     */
    public static Boolean isSpace(String parameter) {
        // 设定查看模式
        Pattern pattern = Pattern.compile(SPACE);
        // 判断parameter是否匹配，返回匹配结果
        Matcher matcher = pattern.matcher(parameter);
        // 返回结果（true）
        return matcher.matches();
    }

    /**
     * 正则·日期校验
     */
    public static Boolean isDate(String parameter) {
        // 设定查看模式
        Pattern pattern = Pattern.compile(DATE);
        // 判断parameter是否匹配，返回匹配结果
        Matcher matcher = pattern.matcher(parameter);
        // 返回结果（true）
        return matcher.matches();
    }

    /**
     * 正则·时间校验
     */
    public static Boolean isTime(String parameter) {
        // 设定查看模式
        Pattern pattern = Pattern.compile(TIME);
        // 判断parameter是否匹配，返回匹配结果
        Matcher matcher = pattern.matcher(parameter);
        // 返回结果（true）
        return matcher.matches();
    }

    /**
     * 正则·时间校验
     */
    public static Boolean isHttp(String parameter) {
        // 设定查看模式
        Pattern pattern = Pattern.compile(HTTPS);
        // 判断parameter是否匹配，返回匹配结果
        Matcher matcher = pattern.matcher(parameter);
        // 返回结果（true）
        return matcher.matches();
    }

    /**
     * 正则·自定义
     */
    public static Boolean isCustom(String regex, String parameter) {
        // 设定查看模式
        Pattern pattern = Pattern.compile(regex);
        // 判断parameter是否匹配，返回匹配结果
        Matcher matcher = pattern.matcher(parameter);
        // 返回结果（true）
        return matcher.matches();
    }

}