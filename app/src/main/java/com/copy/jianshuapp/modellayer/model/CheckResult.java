package com.copy.jianshuapp.modellayer.model;

import com.google.gson.annotations.SerializedName;

/**
 * 参数检查结果
 * @version imkarl 2017-04
 */
public class CheckResult {
    @SerializedName("ok")
    private boolean successful;

    @Override
    public String toString() {
        return "CheckResult{" +
                "successful=" + successful +
                '}';
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }
}
