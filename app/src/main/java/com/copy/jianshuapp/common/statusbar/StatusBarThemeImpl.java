package com.copy.jianshuapp.common.statusbar;

import android.annotation.TargetApi;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.copy.jianshuapp.common.LogUtils;
import com.copy.jianshuapp.common.RomUtils;
import com.copy.jianshuapp.common.VersionUtils;
import com.copy.jianshuapp.common.reflect.Reflect;
import com.copy.jianshuapp.common.reflect.ReflectException;

/**
 * 状态栏主题（亮色/暗色）-兼容处理
 * @version imkarl 2017-03
 */
class StatusBarThemeImpl {
    static final StatusBarCompat.StatusBarTheme IMPL;

    static {
        if (VersionUtils.isSupport(VersionUtils.M)) {
            IMPL = new MStatusBarImpl();
        } else {
            switch (RomUtils.getRomType()) {
                case MIUI:
                    IMPL = new MIUIStatusBarImpl();
                    break;

                case Flyme:
                    IMPL = new FlymeStatusBarImpl();
                    break;

                default:
                    IMPL = (window, color) -> LogUtils.d("该系统不支持 StatusBarTheme.setStyle()");
                    break;
            }
        }
    }


    private static class MStatusBarImpl implements StatusBarCompat.StatusBarTheme {
        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void setStyle(Window window, StatusBarStyle style) {
            // 设置浅色状态栏时的界面显示
            View decor = window.getDecorView();
            int ui = decor.getSystemUiVisibility();
            if (style == StatusBarStyle.Light) {
                ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decor.setSystemUiVisibility(ui);
        }
    }

    private static class MIUIStatusBarImpl implements StatusBarCompat.StatusBarTheme {
        @Override
        public void setStyle(Window window, StatusBarStyle style) {
            try {
                int darkModeFlag = Reflect.on("android.view.MiuiWindowManager$LayoutParams").field("EXTRA_FLAG_STATUS_BAR_DARK_MODE").get();
                Reflect.on(window.getClass()).call("setExtraFlags", (style == StatusBarStyle.Light) ? darkModeFlag : 0, darkModeFlag);
            } catch (ReflectException e) {
                LogUtils.w(e);
            }
        }
    }

    private static class FlymeStatusBarImpl implements StatusBarCompat.StatusBarTheme {
        @Override
        public void setStyle(Window window, StatusBarStyle style) {
            try {
                int darkModeFlag = Reflect.on(WindowManager.LayoutParams.class).field("MEIZU_FLAG_DARK_STATUS_BAR_ICON").get();
                int value = Reflect.on(WindowManager.LayoutParams.class).field("meizuFlags").get();

                if (style == StatusBarStyle.Light) {
                    value |= darkModeFlag;
                } else {
                    value &= ~darkModeFlag;
                }

                WindowManager.LayoutParams params = window.getAttributes();
                Reflect.on(params).set("meizuFlags", value);
                window.setAttributes(params);
            } catch (ReflectException e) {
                LogUtils.w(e);
            }
        }
    }

}
