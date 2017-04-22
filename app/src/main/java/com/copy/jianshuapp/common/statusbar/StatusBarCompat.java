package com.copy.jianshuapp.common.statusbar;

import android.app.Activity;
import android.content.res.Resources;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.view.Window;

import com.copy.jianshuapp.common.AppUtils;

/**
 * 状态栏兼容处理
 * @version imkarl 2017-03
 * @see <a href="https://github.com/msdx/status-bar-compat">msdx/status-bar-compat</a>
 */
public class StatusBarCompat {
    private StatusBarCompat() { }

    interface StatusBarColor {
        void setColor(Window window, @ColorInt int color);
    }
    interface StatusBarTheme {
        void setStyle(Window window, StatusBarStyle style);
    }
    interface FitsSystemWindows {
        void setFitsSystemWindows(Window window, boolean fitSystemWindows);
    }
    interface StatusBarTransparent {
        void setTransparent(Window window);
    }


    public static void setColorRes(Activity activity, @ColorRes int colorRes) {
        setColor(activity, activity.getResources().getColor(colorRes));
    }
    public static void setColor(Activity activity, @ColorInt int color) {
        StatusBarColorImpl.IMPL.setColor(activity.getWindow(), color);
    }

    public static void setStyle(Activity activity, StatusBarStyle style) {
        StatusBarThemeImpl.IMPL.setStyle(activity.getWindow(), style);
    }

    public static void setFitsSystemWindows(Activity activity, boolean fitSystemWindows) {
        FitsSystemWindowsImpl.IMPL.setFitsSystemWindows(activity.getWindow(), fitSystemWindows);
    }

    /**
     * 设置状态栏全透明
     */
    public static void setTransparent(Activity activity) {
        StatusBarTransparentImpl.IMPL.setTransparent(activity.getWindow());
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight() {
        int statusBarHeight = 0;
        Resources res = AppUtils.getContext().getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

}
