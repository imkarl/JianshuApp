package com.copy.jianshuapp.exception;

/**
 * @author alafighting 2016-01
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

}
