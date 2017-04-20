package com.copy.jianshuapp.utils;

import com.copy.jianshuapp.common.ObjectUtils;

import java.util.regex.Pattern;

/**
 * 格式检查相关的工具类
 * @version imkarl 2017-03
 */
public class CheckFormatUtils {
    private CheckFormatUtils() {
    }

    /**
     * 正则：用户昵称
     * 只能包含英文、汉字、数字及下划线，长度为4-15个字符
     */
    private static final String REGEX_NICKNAME = "[\\u4e00-\\u9fa5_a-zA-Z0-9]{4,15}";
    /**
     * 正则：密码
     * 不能包含任何空白字符【中英文空格\f\n\r\t\v】及汉字，长度不能少于6个字符
     */
    private static final String REGEX_PASSWORD = "[^\\s\\u4e00-\\u9fa5]{6,}";
    /**
     * 正则：手机号（精确）
     * <p>移动：134(0-8)、135、136、137、138、139、147、150、151、152、157、158、159、178、182、183、184、187、188</p>
     * <p>联通：130、131、132、145、155、156、175、176、185、186</p>
     * <p>电信：133、153、173、177、180、181、189</p>
     * <p>全球星：1349</p>
     * <p>虚拟运营商：170</p>
     */
    public static final String REGEX_PHONE  = "^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|(147))\\d{8}$";
    /**
     * 正则：邮箱
     */
    public static final String REGEX_EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";


    /**
     * 判断是否匹配正则
     * @param regex 正则表达式
     * @param input 要匹配的字符串
     * @return {@code true}: 匹配<br>{@code false}: 不匹配
     */
    public static boolean isMatch(String regex, CharSequence input) {
        return !ObjectUtils.isEmpty(input) && Pattern.matches(regex, input);
    }

    /**
     * 用户昵称是否合法
     * @return true:格式合法，false:非法
     */
    public static boolean isNickname(String text) {
        return isMatch(REGEX_NICKNAME, text);
    }

    /**
     * 手机号是否合法
     * @return true:格式合法，false:非法
     */
    public static boolean isPhone(String text) {
        return isMatch(REGEX_PHONE, text);
    }

    /**
     * 邮箱是否合法
     * @return true:格式合法，false:非法
     */
    public static boolean isEmail(String text) {
        return isMatch(REGEX_EMAIL, text);
    }

    /**
     * 密码是否合法
     * @return true:格式合法，false:非法
     */
    public static boolean isPassword(String text) {
        return isMatch(REGEX_PASSWORD, text);
    }

}
