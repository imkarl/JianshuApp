package com.copy.jianshuapp.modellayer.repository;

import com.copy.jianshuapp.common.DeviceInfo;
import com.copy.jianshuapp.common.DisplayInfo;
import com.copy.jianshuapp.common.LogUtils;
import com.copy.jianshuapp.common.ObjectUtils;
import com.copy.jianshuapp.exception.ApiException;
import com.copy.jianshuapp.exception.NullException;
import com.copy.jianshuapp.modellayer.model.Account;
import com.copy.jianshuapp.modellayer.remote.ErrorCode;
import com.copy.jianshuapp.modellayer.remote.ErrorMsg;
import com.copy.jianshuapp.modellayer.remote.RemoteManager;
import com.copy.jianshuapp.modellayer.remote.api.UserApi;
import com.copy.jianshuapp.utils.Constants;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

import cn.imkarl.urlbuilder.UrlQuery;
import io.reactivex.Observable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * 用户Repository
 * @version imkarl 2017-04
 */
public final class UserRepository extends Repository {
    private UserRepository() {
    }

    private static final UserApi userApi = RemoteManager.getInstance().createApi(UserApi.class);

    /**
     * 检查用户手机号码是否有效
     * @param phone 手机号码
     */
    public static Observable<Boolean> checkPhone(String phone) {
        JsonObject json = new JsonObject();
        json.addProperty("mobile_number", phone);
        json.addProperty("mobile_number_country_code", "CN");
        return userApi.checkPhone(json)
                .flatMap(it -> {
                    if (it.isSuccessful()) {
                        return Observable.just(true);
                    }
                    return Observable.error(new ApiException(new ErrorMsg(ErrorCode.PARAMETER_PHONE_OCCUPIED, "手机号已注册")));
                })
                .compose(process());
    }
    /**
     * 检查昵称是否有效
     * @param nickname 昵称
     */
    public static Observable<Boolean> checkNickname(String nickname) {
        JsonObject json = new JsonObject();
        json.addProperty("nickname", nickname);
        return userApi.checkNickname(json)
                .map(it -> true)
                .compose(process());
    }


    private static final Map<String, ErrorCode> ERROR_MESSAGES = new HashMap<String, ErrorCode>() {{
        put("昵称 已经被使用", ErrorCode.PARAMETER_NICKNAME_OCCUPIED);
        put("验证码无效或已过期", ErrorCode.PARAMETER_CAPTCHA_WRONG);
    }};
    /**
     * 用户注册
     */
    public static Observable<Boolean> register(String nickname, String phone, String password, String captcha, UrlQuery query) {
        if (ObjectUtils.isEmpty(query)) {
            query = new UrlQuery();
        }

        query.put("user[nickname]", nickname);
        query.put("user[mobile_number_country_code]", "CN");
        query.put("user[mobile_number]", phone);
        query.put("user[password]", password);
        query.put("sms_code", captcha);

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=UTF-8"), query.build(true));
        return userApi.register(body)
                .flatMap(html -> {
                    if (ObjectUtils.isEmpty(html)) {
                        return Observable.error(new NullException("无响应数据"));
                    }

                    for (String key : ERROR_MESSAGES.keySet()) {
                        if (html.contains(key)) {
                            return Observable.error(new ApiException(new ErrorMsg(ERROR_MESSAGES.get(key), key)));
                        }
                    }

                    LogUtils.e("html=>\n"+html);

                    return Observable.just(true);
                })
                .compose(process());
    }

    /**
     * 用户登录
     */
    public static Observable<Account> login(String account, String password) {
        int deviceWidth = DisplayInfo.getWidthPixels();
        int deviceHeight = DisplayInfo.getRealHeightPixels();
        String deviceOs = Constants.OS_ANDROID;
        String deviceCpu = DeviceInfo.getCpu();
        int deviceOsVersion = DeviceInfo.getSdkVersion();
        String deviceModel = DeviceInfo.getModel();
        String deviceBrand = DeviceInfo.getBrand();
        String deviceUniqueId = DeviceInfo.getUniqueId();
        String appName = "haruki";  // AppInfo.getName(); // haruki
        String appVersion = "2.1.3";  // AppInfo.getVersionName(); // 2.1.3

        return userApi.login(account, password,
                deviceWidth, deviceHeight, deviceCpu, deviceOs, deviceOsVersion,
                deviceModel, deviceBrand, deviceUniqueId, appVersion, appName)
                .compose(process());
    }

}
