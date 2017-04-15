package com.copy.jianshuapp.exception;

import com.copy.jianshuapp.common.AppUtils;
import com.copy.jianshuapp.common.ObjectUtils;

import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.HttpException;

/**
 * 异常相关
 * @version imkarl 2016-10
 */
public class ExceptionUtils {

    /**
     * 获取人性化的描述
     */
    public static String getDescription(Throwable throwable) {
        // null
        if (throwable == null) {
            return "未知错误";
        }

        // 根据异常类型分别处理
        if (throwable instanceof Exception) {
            return ((Exception) throwable).getDescription();
        }
        if (!AppUtils.isNetworkConnected()) {
            // 未连接网络
            return "请检查网络设置~";
        }
        if (throwable instanceof UnknownHostException) {
            // 找不到服务器（可能是未连接网络，导致域名解析失败）
            return "连接服务器失败~";
        }
        if (throwable instanceof SocketTimeoutException) {
            return "网络超时~";
        }
        if (throwable instanceof SocketException) {
            return "网络千里之外~";
        }
        if (throwable instanceof HttpException) {
            int httpCode = ((HttpException) throwable).code();
            return "服务器异常 " + httpCode;
        }

        // 默认处理
        String errMessage = throwable.getMessage();
        if (ObjectUtils.isEmpty(errMessage)) {
            errMessage = "未知错误";
        }
        return errMessage;
    }

}
