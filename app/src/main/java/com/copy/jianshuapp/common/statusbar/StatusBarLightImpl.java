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
 * 状态栏亮色-兼容处理
 * @version imkarl 2017-03
 */
class StatusBarLightImpl {
    static final StatusBarCompat.StatusBarLight IMPL;

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
                    IMPL = (window, color) -> LogUtils.d("不支持 setStatusBarLight() 的版本");
                    break;
            }
        }
    }


    private static class MStatusBarImpl implements StatusBarCompat.StatusBarLight {
        @TargetApi(Build.VERSION_CODES.M)
        @Override
        public void setStatusBarLight(Window window, boolean lightStatusBar) {
            // 设置浅色状态栏时的界面显示
            View decor = window.getDecorView();
            int ui = decor.getSystemUiVisibility();
            if (lightStatusBar) {
                ui |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            } else {
                ui &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            }
            decor.setSystemUiVisibility(ui);
        }
    }

    private static class MIUIStatusBarImpl implements StatusBarCompat.StatusBarLight {
        @Override
        public void setStatusBarLight(Window window, boolean lightStatusBar) {
            try {
                int darkModeFlag = Reflect.on("android.view.MiuiWindowManager$LayoutParams").field("EXTRA_FLAG_STATUS_BAR_DARK_MODE").get();
                Reflect.on(window.getClass()).call("setExtraFlags", lightStatusBar ? darkModeFlag : 0, darkModeFlag);
            } catch (ReflectException e) {
                LogUtils.w(e);
            }
        }
    }

    private static class FlymeStatusBarImpl implements StatusBarCompat.StatusBarLight {
        @Override
        public void setStatusBarLight(Window window, boolean lightStatusBar) {
            try {
                int darkModeFlag = Reflect.on(WindowManager.LayoutParams.class).field("MEIZU_FLAG_DARK_STATUS_BAR_ICON").get();
                int value = Reflect.on(WindowManager.LayoutParams.class).field("meizuFlags").get();

                if (lightStatusBar) {
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
