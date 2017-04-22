package com.copy.jianshuapp.common.statusbar;

import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

/**
 * FitsSystemWindows-兼容处理
 * @version imkarl 2017-04
 */
class FitsSystemWindowsImpl {
    static final StatusBarCompat.FitsSystemWindows IMPL;

    static {
        IMPL = (window, fitSystemWindows) -> {
            ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
            View mChildView = mContentView.getChildAt(0);
            if (mChildView != null) {
                // 注意不是设置 ContentView 的 FitsSystemWindows, 而是设置 ContentView 的第一个子 View . 预留出系统 View 的空间.
                mChildView.setFitsSystemWindows(fitSystemWindows);
            }
        };
    }

}
