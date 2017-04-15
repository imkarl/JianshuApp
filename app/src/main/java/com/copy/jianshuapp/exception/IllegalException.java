package com.copy.jianshuapp.exception;

/**
 * 非法操作
 * @version imkarl 2017-03
 */
public class IllegalException extends Exception {
    public IllegalException() {
    }

    public IllegalException(String detailMessage) {
        super(detailMessage);
    }

    public IllegalException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public IllegalException(Throwable throwable) {
        super(throwable);
    }
}
