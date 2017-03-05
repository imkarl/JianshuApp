package com.copy.jianshuapp.uilayer;

import android.os.Bundle;

import com.copy.jianshuapp.modellayer.local.SettingsUtils;
import com.copy.jianshuapp.uilayer.base.BaseActivity;
import com.copy.jianshuapp.uilayer.guide.GuideActivity;

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
//                        startActivity(MainActivity.class);
                        startActivity(GuideActivity.class);
                    } else {
                        startActivity(GuideActivity.class);
                    }
                    SettingsUtils.put(SettingsUtils.HAS_SHOW_GUIDE, true);

                    super.finish();
                });
    }

}
