package com.copy.jianshuapp.common;

import android.os.Build;

/**
 * Android SDK 版本判断
 * @author imkarl 2016-02
 */
public class VersionUtils extends Build.VERSION_CODES {

    private VersionUtils() {
    }

    /**
     * 是否支持指定版本
     * @see Build.VERSION_CODES
     */
    public static boolean isSupport(int version) {
        return Build.VERSION.SDK_INT >= version;
    }

}
