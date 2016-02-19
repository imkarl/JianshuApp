package com.copy.jianshuapp.exception;

/**
 * 异常处理器
 * @author alafighting 2016-02
 */
public interface OnExceptionHandler {

    /**
     * 处理异常
     * @param exception 错误异常
     */
    void handlerException(Throwable exception);

}
