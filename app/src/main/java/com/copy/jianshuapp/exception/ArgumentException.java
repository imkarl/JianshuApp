package com.copy.jianshuapp.exception;

/**
 * @author alafighting 2016-01
 */
public class ArgumentException extends Exception {
    public ArgumentException() {
    }

    public ArgumentException(String detailMessage) {
        super(detailMessage);
    }

    public ArgumentException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public ArgumentException(Throwable throwable) {
        super(throwable);
    }
}
