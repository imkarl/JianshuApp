package com.copy.jianshuapp.modellayer.remote.api;

import com.copy.jianshuapp.modellayer.model.Account;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;

/**
 * 用户API
 * @author imkarl
 */
public interface UserApi {

    Observable<Response> checkPhone(JsonObject json);

    Observable<Object> checkNickname(JsonObject json);

    Observable<String> register(RequestBody body);

    Observable<Account> login(String account, String password,
                              int deviceWidth, int deviceHeight,
                              String deviceCpu, String deviceOs,
                              int deviceOsVersion, String deviceModel,
                              String deviceBrand, String deviceUniqueId,
                              String appVersion, String appName);

}
