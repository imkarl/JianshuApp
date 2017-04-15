package com.copy.jianshuapp.common;

import android.graphics.drawable.Drawable;

/**
 * App基本信息
 * @version imkarl 2017-04
 */
public class AppInfo {

    /** 名称 */
    private String name;
    /** 图标 */
    private Drawable icon;
    /** 包名 */
    private String packageName;
    /** 包路径 */
    private String packagePath;
    /** 版本名称 */
    private String versionName;
    /** 版本号 */
    private int versionCode;
    /** 是否系统应用 */
    private boolean system;

    public AppInfo() {
    }
    public AppInfo(String name, Drawable icon, String packageName, String packagePath, String versionName, int versionCode, boolean system) {
        this.name = name;
        this.icon = icon;
        this.packageName = packageName;
        this.packagePath = packagePath;
        this.versionName = versionName;
        this.versionCode = versionCode;
        this.system = system;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackagePath() {
        return packagePath;
    }

    public void setPackagePath(String packagePath) {
        this.packagePath = packagePath;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public boolean isSystem() {
        return system;
    }

    public void setSystem(boolean system) {
        this.system = system;
    }
}
