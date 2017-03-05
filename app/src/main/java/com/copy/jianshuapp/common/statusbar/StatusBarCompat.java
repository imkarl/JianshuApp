package com.copy.jianshuapp.common.statusbar;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.ColorInt;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * 状态栏兼容处理
 * @version imkarl 2017-03
 * @see <a href="https://github.com/msdx/status-bar-compat">msdx/status-bar-compat</a>
 */
public class StatusBarCompat {
    private StatusBarCompat() { }

    interface StatusBarColor {
        void setStatusBarColor(Window window, @ColorInt int color);
    }
    interface StatusBarLight {
        void setStatusBarLight(Window window, boolean lightStatusBar);
    }

    public static void setStatusBarColor(Window window, @ColorInt int color) {
        StatusBarColorImpl.IMPL.setStatusBarColor(window, color);
    }

    public static void setStatusBarLight(Window window, boolean lightStatusBar) {
        StatusBarLightImpl.IMPL.setStatusBarLight(window, lightStatusBar);
    }

    public static void setFitsSystemWindows(Window window, boolean fitSystemWindows) {
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            // 注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
            mChildView.setFitsSystemWindows(fitSystemWindows);
        }
    }

    /**
     * 获取状态栏高度
     */
    public static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

}
