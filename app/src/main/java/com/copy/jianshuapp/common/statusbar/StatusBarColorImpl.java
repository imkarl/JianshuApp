package com.copy.jianshuapp.common.statusbar;

import android.annotation.TargetApi;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.copy.jianshuapp.common.LogUtils;
import com.copy.jianshuapp.common.RomUtils;
import com.copy.jianshuapp.common.VersionUtils;
import com.copy.jianshuapp.common.rom.RomType;

/**
 * 状态栏颜色-兼容处理
 * @version imkarl 2017-03
 */
class StatusBarColorImpl {
    static final StatusBarCompat.StatusBarColor IMPL;

    static {
        if (VersionUtils.isSupport(VersionUtils.M)) {
            IMPL = new MStatusBarImpl();
        } else if (VersionUtils.isSupport(VersionUtils.LOLLIPOP) && RomUtils.getRomType() != RomType.EMUI) {
            IMPL = new LollipopStatusBarImpl();
        } else if (VersionUtils.isSupport(VersionUtils.KITKAT)) {
            IMPL = new KitkatStatusBarImpl();
        } else {
            IMPL = (window, color) -> LogUtils.d("该系统不支持 StatusBarColor.setColor()");
        }
    }


    private static class MStatusBarImpl implements StatusBarCompat.StatusBarColor {
        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void setColor(Window window, @ColorInt int color) {
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(color);

            // 去掉系统状态栏下的windowContentOverlay
            View v = window.findViewById(android.R.id.content);
            if (v != null) {
                v.setForeground(null);
            }
        }
    }

    private static class LollipopStatusBarImpl implements StatusBarCompat.StatusBarColor {
        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void setColor(Window window, @ColorInt int color) {
            //取消设置透明状态栏,使 ContentView 内容不再覆盖状态栏
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //需要设置这个 flag 才能调用 setColor 来设置状态栏颜色
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            //设置状态栏颜色
            window.setStatusBarColor(color);
        }
    }

    private static class KitkatStatusBarImpl implements StatusBarCompat.StatusBarColor {
        private static final String STATUS_BAR_VIEW_TAG = "ghStatusBarView";

        @TargetApi(Build.VERSION_CODES.KITKAT)
        @Override
        public void setColor(Window window, @ColorInt int color) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            ViewGroup decorViewGroup = (ViewGroup) window.getDecorView();
            View statusBarView = decorViewGroup.findViewWithTag(STATUS_BAR_VIEW_TAG);
            if (statusBarView == null) {
                statusBarView = new View(window.getContext());
                statusBarView.setTag(STATUS_BAR_VIEW_TAG);
                int statusBarHeight = StatusBarCompat.getStatusBarHeight();
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, statusBarHeight);
                params.gravity = Gravity.TOP;
                statusBarView.setLayoutParams(params);
                decorViewGroup.addView(statusBarView);
            }
            statusBarView.setBackgroundColor(color);
            FitsSystemWindowsImpl.IMPL.setFitsSystemWindows(window, true);
        }
    }

}
