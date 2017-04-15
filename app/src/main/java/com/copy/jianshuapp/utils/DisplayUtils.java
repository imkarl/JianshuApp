package com.copy.jianshuapp.utils;

import android.content.Context;
import android.content.res.Resources;

/**
 * 屏幕相关的工具类
 * @version imkarl 2017-04
 */
public class DisplayUtils {

    public static float getScaledDensity() {
        return Resources.getSystem().getDisplayMetrics().scaledDensity;
    }

}
