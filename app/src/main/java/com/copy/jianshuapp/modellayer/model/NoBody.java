package com.copy.jianshuapp.modellayer.model;

import com.copy.jianshuapp.modellayer.remote.JSResponse;

/**
 * 无实际输出的响应实体
 * @tips 方便只需获取异常信息时使用
 * @version imkarl 2017-04
 */
public class NoBody extends JSResponse {

    @Override
    public String toString() {
        return "NoBody{"
                +"errors=" + getErrors()
                + "}";
    }

}
