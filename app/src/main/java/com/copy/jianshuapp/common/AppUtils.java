package com.copy.jianshuapp.common;

import android.app.Application;
import android.content.Context;

/**
 * App通用工具类
 * @version imkarl 2017-03
 */
public class AppUtils {

    private static Application sContext;
    public static void onCreate(Application app) {
        sContext = app;
    }

    public static Context getContext() {
        return sContext;
    }

}
