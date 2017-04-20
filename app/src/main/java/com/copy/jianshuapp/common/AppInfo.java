package com.copy.jianshuapp.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import com.copy.jianshuapp.R;

/**
 * App基本信息
 * @tips Application.onCreate() 之后才能获取到属性值
 * @version imkarl 2017-04
 */
public class AppInfo {

    /** 名称 */
    private static final String name;
    /** 图标 */
    private static final Drawable icon;
    /** 包名 */
    private static final String packageName;
    /** 包路径 */
    private static final String packagePath;
    /** 版本名称 */
    private static final String versionName;
    /** 版本号 */
    private static final int versionCode;
    /** 是否系统应用 */
    private static final boolean system;

    static {
        Context context = AppUtils.getContext();
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.w(e);
        }

        if (pi != null) {
            ApplicationInfo ai = pi.applicationInfo;

            name = ai.loadLabel(pm).toString();
            icon = ai.loadIcon(pm);
            packageName = pi.packageName;
            packagePath = ai.sourceDir;
            versionName = pi.versionName;
            versionCode = pi.versionCode;
            system = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != 0;
        } else {
            name = AppUtils.getContext().getResources().getString(R.string.app_name);
            icon = AppUtils.getContext().getResources().getDrawable(R.mipmap.jianshu_icon);
            packageName = AppUtils.getContext().getPackageName();
            packagePath = AppUtils.getContext().getPackageCodePath();
            versionName = "";
            versionCode = 0;
            system = false;
        }
    }

    public AppInfo() {
    }

    @Override
    public String toString() {
        return "AppInfo{" +
                "name='" + name + '\'' +
                ", icon=" + icon +
                ", packageName='" + packageName + '\'' +
                ", packagePath='" + packagePath + '\'' +
                ", versionName='" + versionName + '\'' +
                ", versionCode=" + versionCode +
                ", system=" + system +
                '}';
    }

    public static String getName() {
        return name;
    }
    public static Drawable getIcon() {
        return icon;
    }
    public static String getPackageName() {
        return packageName;
    }
    public static String getPackagePath() {
        return packagePath;
    }
    public static String getVersionName() {
        return versionName;
    }
    public static int getVersionCode() {
        return versionCode;
    }
    public static boolean isSystem() {
        return system;
    }

}
