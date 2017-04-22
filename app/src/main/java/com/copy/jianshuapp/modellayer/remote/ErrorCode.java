package com.copy.jianshuapp.modellayer.remote;

import com.google.gson.annotations.SerializedName;

/**
 * 响应状态码
 * @version imkarl 2017-04
 */
public enum ErrorCode {
    @SerializedName("-1")
    UNKNOWN("未知错误"),

    @SerializedName("888")
    PARAMETER_NICKNAME_OCCUPIED("昵称已经被使用"),
    @SerializedName("0000")
    PARAMETER_CAPTCHA_WRONG("验证码错误"),
    @SerializedName("0000")
    PARAMETER_PHONE_OCCUPIED("手机号已经被使用"),

    @SerializedName("1")
    CLIENT_VALID_FAIL("应用验证失败"),

    @SerializedName("201")
    LOGIN_FAIL("登录邮箱/手机号码或密码错误"),



    @SerializedName("10000")
    SUCCESS("成功"),
    @SerializedName("10001")
    SYS_ERROR("系统错误"),
    @SerializedName("10002")
    SYS_BUSY("系统繁忙"),
    @SerializedName("10003")
    SYS_PAUSE("服务暂停"),
    @SerializedName("10004")
    SYS_RESPONSE_FAIL("服务响应失败"),
    @SerializedName("10005")
    SYS_TIMEOUT("处理超时"),

    @SerializedName("11000")
    SYS_ILLEGAL_IP("IP限制，不能请求该资源"),
    @SerializedName("11001")
    SYS_ILLEGAL_USER("非法用户，不能请求该资源"),
    @SerializedName("11002")
    SYS_NEED_LOGIN("需要登陆，不能请求该资源"),
    @SerializedName("11003")
    SYS_PERMISSION_DENIED("权限不足，不能请求该资源"),
    @SerializedName("11004")
    SYS_ILLEGAL_REQUEST("非法请求，不能请求该资源"),
    @SerializedName("11005")
    SYS_EXCEEDED_REQUEST_COUNT("超过请求频次"),

    @SerializedName("12000")
    SYS_PARAMETER_ILLEGAL("参数非法"),
    @SerializedName("12001")
    SYS_PARAMETER_MISSING("缺少参数"),
    @SerializedName("12002")
    SYS_PARAMETER_TOO_LONG("参数长度超过限制"),
    @SerializedName("12003")
    SYS_UNSUPPORTED_FILE_TYPE("不支持的文件类型"),
    @SerializedName("12004")
    SYS_UNSUPPORTED_IMAGE_TYPE("不支持的图片类型"),
    @SerializedName("12005")
    SYS_SIMILAR_CONTENT("提交相似的内容"),
    @SerializedName("12006")
    SYS_LAW_CONTENT("包含非法内容(色情/暴力等)"),

    @SerializedName("13000")
    SYS_NONSUPPORT_API("接口不存在"),
    @SerializedName("13001")
    SYS_NONSUPPORT_METHOD("不支持的Method(POST/GET)"),
    @SerializedName("13002")
    SYS_NONSUPPORT_ENCTYPE("不支持的enctype(表单编码格式)"),

    @SerializedName("21000")
    USER_NOT_EXISTENT("用户不存在"),
    ;

    private final String description;

    ErrorCode(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
