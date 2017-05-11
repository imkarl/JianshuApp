package com.copy.jianshuapp.uilayer;

import android.os.Bundle;

import com.copy.jianshuapp.common.AppUtils;
import com.copy.jianshuapp.modellayer.local.SettingsUtils;
import com.copy.jianshuapp.uilayer.base.BaseActivity;
import com.copy.jianshuapp.uilayer.guide.GuideActivity;
import com.copy.jianshuapp.uilayer.home.activitys.MainActivity;
import com.copy.jianshuapp.uilayer.login.activity.LoginActivity;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 闪屏页
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Observable.timer(500, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(it -> {
                    // 判断是否应该显示引导页
                    if (SettingsUtils.getBoolean(SettingsUtils.HAS_SHOW_GUIDE, false)) {
                        if (AppUtils.isLogin()) {
                            // 主页
                            startActivity(MainActivity.class);
                        } else {
                            // 判断是否有登录或注册成功过
                            if (SettingsUtils.getBoolean(SettingsUtils.HAS_LOGIN)
                                    || SettingsUtils.getBoolean(SettingsUtils.HAS_REGIST)) {
                                LoginActivity.launchLogin();
                            } else {
                                LoginActivity.launchRegister();
                            }
                        }
                    } else {
                        // 引导页
                        SettingsUtils.put(SettingsUtils.HAS_SHOW_GUIDE, true);
                        GuideActivity.launch();
                    }

                    super.finish();
                });
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

}
