package com.copy.jianshuapp.exception;

/**
 * 登录失效
 */
public class LoginInvalidException extends Exception {

    public LoginInvalidException() {
    }
    public LoginInvalidException(String detailMessage) {
        super(detailMessage);
    }
    public LoginInvalidException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }
    public LoginInvalidException(Throwable throwable) {
        super(throwable);
    }

    @Override
    public String getDescription() {
        return "登录失效";
    }

}
