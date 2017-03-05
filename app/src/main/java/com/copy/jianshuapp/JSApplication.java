package com.copy.jianshuapp;

import android.app.Application;

import com.copy.jianshuapp.common.AppUtils;
import com.copy.jianshuapp.common.LogUtils;
import com.copy.jianshuapp.exception.CrashHandlerHelper;
import com.copy.jianshuapp.exception.OnExceptionHandler;

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
    public void onCreate() {
        super.onCreate();
        AppUtils.onCreate(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mCrashHandler.unregist();
    }

}
