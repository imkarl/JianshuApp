package com.copy.jianshuapp.exception;

import com.copy.jianshuapp.common.ObjectUtils;

/**
 * 异常基类
 * @version imkarl 2016-08
 */
public class Exception extends RuntimeException {

    public Exception() {
    }

    public Exception(String detailMessage) {
        super(detailMessage);
    }

    public Exception(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public Exception(Throwable throwable) {
        super(throwable);
    }


    /**
     * 获取人性化的描述
     */
    public String getDescription() {
        String msg = getMessage();
        if (ObjectUtils.isEmpty(msg)) {
            msg = String.valueOf(getClass());
        }
        return msg;
    }

}
