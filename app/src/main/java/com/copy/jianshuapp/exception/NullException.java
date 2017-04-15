package com.copy.jianshuapp.exception;

/**
 * 对象为空
 * @version imkarl 2016-08
 */
public class NullException extends Exception {
    public NullException() {
    }

    public NullException(String detailMessage) {
        super(detailMessage);
    }

    public NullException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public NullException(Throwable throwable) {
        super(throwable);
    }
}
