package com.copy.jianshuapp;

import android.app.Application;
import android.content.Context;

import com.copy.jianshuapp.common.ActivityLifcycleManager;
import com.copy.jianshuapp.common.AppUtils;
import com.copy.jianshuapp.common.LogUtils;
import com.copy.jianshuapp.common.VersionChannel;
import com.copy.jianshuapp.exception.CrashHandlerHelper;
import com.vanniktech.emoji.EmojiManager;
import com.vanniktech.emoji.ios.IosEmojiProvider;

/**
 * 全局应用程序类
 * @author imkarl 2017-03
 */
public class JSApplication extends Application {

    private CrashHandlerHelper mCrashHandler;

    public JSApplication() {
        super();
        mCrashHandler = CrashHandlerHelper.regist();
        mCrashHandler.addHandler(exception -> {
            LogUtils.e(exception);
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        AppUtils.attachBaseContext(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        AppUtils.onCreate();

        // 注册Activity生命周期回调监听
        registerActivityLifecycleCallbacks(ActivityLifcycleManager.get());
        // emoji
        EmojiManager.install(new IosEmojiProvider());
    }

}
