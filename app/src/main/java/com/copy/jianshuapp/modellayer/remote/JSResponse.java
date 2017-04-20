package com.copy.jianshuapp.modellayer.remote;

import com.copy.jianshuapp.common.ObjectUtils;
import com.copy.jianshuapp.exception.ApiException;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 响应数据-基类
 * @version imkarl 2017-04
 */
public abstract class JSResponse {

    @SerializedName("error")
    private List<ErrorMsg> errors;

    /**
     * 是否为成功
     */
    public boolean isSuccessful() {
        return ObjectUtils.isEmpty(errors);
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
    public String getErrorDescription() {
        return ApiException.getDescription(errors);
    }

    @Override
    public String toString() {
        return "JSResponse{" +
                "errors=" + errors +
                '}';
    }

    public List<ErrorMsg> getErrors() {
        return errors;
    }
    public void setErrors(List<ErrorMsg> errors) {
        this.errors = errors;
    }
}
