package com.copy.jianshuapp.common;

import android.app.Application;
import android.os.Build;

import com.copy.jianshuapp.BuildConfig;
import com.copy.jianshuapp.common.reflect.Reflect;

/**
 * 版本途径
 * @version imkarl 2017-01
 */
public enum VersionChannel {
    Debug, // Debug模式
    Beta, // 内测版
    UnitTest, // 单元测试
    Stable, // 稳定版
    ;


    /** 当前版本 */
    private static VersionChannel sCurrentChannel = VersionChannel.Stable;
    static {
        if (BuildConfig.DEBUG) {
            sCurrentChannel = VersionChannel.Debug;
        }
    }

    static void attachBaseContext(Application application) {
        // 判断是否单元测试
        String packageResourcePath = application.getPackageResourcePath().toLowerCase();
        boolean isUnitTest = !(packageResourcePath.startsWith("/data/")
                || packageResourcePath.startsWith("/mnt/")
                || packageResourcePath.startsWith("/storage/"));
        if (isUnitTest) {
            sCurrentChannel = VersionChannel.UnitTest;
        }
    }
    static void onCreate() {
        // Application.onCreate() 之后才能获取到AppInfo
        AppInfo appInfo = AppUtils.getAppInfo(AppUtils.getContext());
        if (appInfo == null) {
            return;
        }

        // 判断是否内测版
        String versionName = appInfo.getVersionName();
        boolean isBetaVersion = versionName.startsWith("beta");
        if (isBetaVersion && sCurrentChannel!= VersionChannel.UnitTest) {
            sCurrentChannel = VersionChannel.Beta;
        }
    }

    public static VersionChannel getCurent() {
        return sCurrentChannel;
    }

    /**
     * 是否Debug模式
     */
    public static boolean isDebug() {
        return getCurent()== VersionChannel.Debug;
    }
    /**
     * 是否单元测试模式
     */
    public static boolean isUnitTest() {
        return getCurent()== VersionChannel.UnitTest;
    }
    /**
     * 是否内测版
     */
    public static boolean isBeta() {
        return getCurent()== VersionChannel.Beta;
    }
    /**
     * 是否稳定版
     */
    public static boolean isStable() {
        return getCurent()== VersionChannel.Stable;
    }

    /**
     * 判断当前设备是否是模拟器
     * @return true:当前是模拟器，false:不是模拟器
     */
    public static boolean isEmulator() {
        boolean isEmulator = Build.HARDWARE.startsWith("vbox");

        if (!isEmulator) {
            isEmulator = Build.MODEL.contains("sdk") || Build.MODEL.equals("Genymotion") || Build.MODEL.contains("- API ");
        }

        try {
            if (!isEmulator) {
                isEmulator = "1".equals(Reflect.on(Build.class).call("getString", "ro.kernel.qemu").get());
            }
        } catch (Exception ignored) { }
        return isEmulator;
    }

}