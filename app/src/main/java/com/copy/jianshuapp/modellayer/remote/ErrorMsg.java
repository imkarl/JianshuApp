package com.copy.jianshuapp.modellayer.remote;

import com.google.gson.annotations.SerializedName;

/**
 * 错误信息
 * @version imkarl 2017-04
 */
public class ErrorMsg {
    @SerializedName("code")
    private ErrorCode code;
    @SerializedName("message")
    private String message;

    public ErrorMsg() {
    }
    public ErrorMsg(ErrorCode code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ErrorMsg{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }

    public ErrorCode getCode() {
        return code;
    }
    public void setCode(ErrorCode code) {
        this.code = code;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
