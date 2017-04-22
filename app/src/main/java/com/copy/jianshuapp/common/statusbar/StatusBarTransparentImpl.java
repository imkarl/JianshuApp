package com.copy.jianshuapp.common.statusbar;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.copy.jianshuapp.common.LogUtils;
import com.copy.jianshuapp.common.VersionUtils;

/**
 * 透明状态栏-兼容处理
 * @version imkarl 2017-04
 */
class StatusBarTransparentImpl {
    static final StatusBarCompat.StatusBarTransparent IMPL;

    static {
        if (VersionUtils.isSupport(VersionUtils.LOLLIPOP)) {
            IMPL = new LollipopTransparentImpl();
        } else if (VersionUtils.isSupport(VersionUtils.KITKAT)) {
            IMPL = new KitkatTransparentImpl();
        } else {
            IMPL = window -> LogUtils.d("该系统不支持 StatusBarTransparent.setTransparent()");
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static class LollipopTransparentImpl implements StatusBarCompat.StatusBarTransparent {
        @Override
        public void setTransparent(Window window) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static class KitkatTransparentImpl implements StatusBarCompat.StatusBarTransparent {
        @Override
        public void setTransparent(Window window) {
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }


}
