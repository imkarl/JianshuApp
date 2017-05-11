package com.copy.jianshuapp.modellayer.repository;

import com.copy.jianshuapp.common.LogUtils;
import com.copy.jianshuapp.common.ObjectUtils;
import com.copy.jianshuapp.common.events.UserEvent;
import com.copy.jianshuapp.common.rx.RxBus;
import com.copy.jianshuapp.exception.ApiException;
import com.copy.jianshuapp.exception.JsonParserException;
import com.copy.jianshuapp.exception.LoginInvalidException;
import com.copy.jianshuapp.exception.NullException;
import com.copy.jianshuapp.modellayer.remote.RemoteManager;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import retrofit2.Response;

/**
 * Repository基类
 * @version imkarl 2017-04
 */
abstract class Repository {

    // 缓存默认有效期
    private static final int DEFAULT_EXPIRES = 12 * 60 * 60 * 1000;

    static <R> ObservableTransformer<R, R> process() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .onErrorResumeNext(throwable -> {
                    ErrorResponse errorResponse = null;

                    if (throwable instanceof HttpException) {
                        // 拦截非 2xx 的HTTP响应，如果响应数据是JSON，则返回ApiException
                        Response response = ((HttpException) throwable).response();
                        if (response != null && response.errorBody() != null) {
                            ResponseBody body = response.errorBody();
                            try {
                                errorResponse = RemoteManager.getInstance().gson().fromJson(body.charStream(), new TypeToken<ErrorResponse>(){}.getType());
                            } catch (Throwable ignored) {
                            }
                        }
                    } else if (throwable instanceof JsonParserException) {
                        // 拦截JSON解析异常，如果响应数据是JSON，则返回ApiException
                        JsonElement json = ((JsonParserException) throwable).json();
                        if (json != null && json.isJsonObject()) {
                            try {
                                errorResponse = RemoteManager.getInstance().gson().fromJson(json, new TypeToken<ErrorResponse>(){}.getType());
                            } catch (Throwable ignored) {
                            }
                        }
                    }

                    if (errorResponse != null && !ObjectUtils.isEmpty(errorResponse.getErrors())) {
                        return Observable.error(new ApiException(errorResponse.getErrors()));
                    }
                    return Observable.error(throwable);
                })
                .flatMap(it -> {
                    if (ObjectUtils.isEmpty(it)) {
                        return Observable.error(new NullException("无响应数据"));
                    }

                    return Observable.just(it);
                })
                .doOnError(error -> {
                    // 登录失效时，通过RxBus发送广播
                    if (error instanceof LoginInvalidException) {
                        LogUtils.w("process()  RxBus.post(UserEvent.INVALID)");
                        RxBus.post(UserEvent.INVALID);
                    }
                });
    }

}
