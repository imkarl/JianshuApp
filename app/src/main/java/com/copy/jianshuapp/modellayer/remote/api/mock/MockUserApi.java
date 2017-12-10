package com.copy.jianshuapp.modellayer.remote.api.mock;

import com.copy.jianshuapp.modellayer.model.Account;
import com.copy.jianshuapp.modellayer.remote.api.UserApi;
import com.google.gson.JsonObject;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.Response;

public class MockUserApi implements UserApi {
    @Override
    public Observable<Response> checkPhone(JsonObject json) {
        return Observable.just(Response.success(0));
    }

    @Override
    public Observable<Object> checkNickname(JsonObject json) {
        return Observable.just(0);
    }

    @Override
    public Observable<String> register(RequestBody body) {
        return Observable.just("success");
    }

    @Override
    public Observable<Account> login(String account, String password, int deviceWidth, int deviceHeight, String deviceCpu, String deviceOs, int deviceOsVersion, String deviceModel, String deviceBrand, String deviceUniqueId, String appVersion, String appName) {
        Account account1 = new Account();
        account1.setId(1);
        account1.setEmail("xxx@mail.com");
        account1.setPhone("13xxxxxxxxxx");
        account1.setNickname(account);
        account1.setAvatar("xxx.jpg");
        return Observable.just(account1);
    }
}
