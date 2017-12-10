package com.copy.jianshuapp.modellayer.remote.interceptors;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * 统一参数管理Interceptor
 * @author imkarl
 */
public class UnifiedParameterInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        return chain.proceed(chain.request());
    }
}
