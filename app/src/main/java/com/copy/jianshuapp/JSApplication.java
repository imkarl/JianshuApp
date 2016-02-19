package com.copy.jianshuapp;

import android.app.Application;

import com.copy.jianshuapp.exception.CrashHandlerHelper;
import com.copy.jianshuapp.exception.OnExceptionHandler;
import com.copy.jianshuapp.utils.log.JSLog;

/**
 * @author alafighting 2016-01-21
 */
public class JSApplication extends Application {

    private CrashHandlerHelper mCrashHandler;

    public JSApplication() {
        super();
        mCrashHandler = CrashHandlerHelper.regist();
        mCrashHandler.addHandler(new OnExceptionHandler() {
            @Override
            public void handlerException(Throwable exception) {
                JSLog.e(exception);
            }
        });

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        mCrashHandler.unregist();
    }

}
