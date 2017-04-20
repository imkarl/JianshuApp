package com.copy.jianshuapp.exception;

import com.copy.jianshuapp.common.ObjectUtils;
import com.copy.jianshuapp.modellayer.remote.ErrorCode;
import com.copy.jianshuapp.modellayer.remote.ErrorMsg;

import java.util.Arrays;
import java.util.List;

/**
 * API响应的异常信息
 * @version imkarl 2017-04
 */
public class ApiException extends Exception {

    private final List<ErrorMsg> errors;

    public ApiException(List<ErrorMsg> errors, String detailMessage) {
        super(detailMessage);
        this.errors = errors;
    }
    public ApiException(List<ErrorMsg> errors) {
        super();
        this.errors = errors;
    }
    public ApiException(ErrorMsg... errors) {
        this(Arrays.asList(errors));
    }
    public ApiException(ErrorCode errorCode) {
        this(new ErrorMsg(errorCode, errorCode.getDescription()));
    }

    @Override
    public String getDescription() {
        return getDescription(errors);
    }

    /**
     * 是否存在该响应码
     */
    public boolean contains(ErrorCode code) {
        if (ObjectUtils.isEmpty(errors)) {
            return false;
        }

        for (ErrorMsg error : errors) {
            if (error.getCode() == code) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取异常描述
     */
    public static String getDescription(List<ErrorMsg> errors) {
        StringBuilder description;
        if (ObjectUtils.isEmpty(errors)) {
            description = new StringBuilder(ErrorCode.UNKNOWN.getDescription());
        } else if (errors.size() == 1) {
            description = new StringBuilder(errors.get(0).getCode().getDescription());
        } else {
            description = new StringBuilder("[");
            for (ErrorMsg error : errors) {
                String msg = error.getMessage();
                if (ObjectUtils.isEmpty(msg)) {
                    msg = error.getCode().getDescription();
                }
                description.append(msg).append(',');
            }
            description.deleteCharAt(description.length() - 1);
            description.append(']');
        }
        return description.toString();
    }

    @Override
    public String toString() {
        return super.toString() + "\n\terrors: " + getDescription();
    }

    public List<ErrorMsg> getErrors() {
        return errors;
    }
}
