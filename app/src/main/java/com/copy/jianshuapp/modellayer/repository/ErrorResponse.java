package com.copy.jianshuapp.modellayer.repository;

import com.copy.jianshuapp.common.ObjectUtils;
import com.copy.jianshuapp.exception.ApiException;
import com.copy.jianshuapp.modellayer.remote.ErrorCode;
import com.copy.jianshuapp.modellayer.remote.ErrorMsg;
import com.google.gson.annotations.SerializedName;
import com.litesuits.orm.db.annotation.Ignore;

import java.util.List;

/**
 * 异常响应数据
 * @version imkarl 2017-05
 */
class ErrorResponse {

    @Ignore
    @SerializedName("error")
    private List<ErrorMsg> errors;

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
