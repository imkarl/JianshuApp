package com.copy.jianshuapp.utils.version;

import android.os.Build;

/**
 * Android SDK 版本判断
 * @author alafighting 2016-02
 */
public class AndroidVersion extends Build.VERSION_CODES {

    private AndroidVersion() {
    }

    /**
     * 是否支持指定版本
     * @param version
     * @return
     * @see Build.VERSION_CODES
     */
    public static boolean isSupport(int version) {
        if (android.os.Build.VERSION.SDK_INT >= version) {
            return true;
        }
        return false;
    }

}
