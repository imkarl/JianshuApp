package com.copy.jianshuapp.common;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * 屏幕相关信息
 * @version imkarl 2017-04
 */
public class DisplayInfo {

    private DisplayInfo() {
    }

    private static final int widthPixels;
    private static final int heightPixels;
    private static final int realWidthPixels;
    private static final int realHeightPixels;
    private static final float density;
    private static final int densityDpi;
    private static final float scaledDensity;
    private static final float xdpi;
    private static final float ydpi;

    static {
        // 屏幕信息
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) AppUtils.getContext().getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);

        widthPixels = dm.widthPixels;
        heightPixels = dm.heightPixels;
        density = dm.density;
        densityDpi = dm.densityDpi;
        scaledDensity = dm.scaledDensity;
        xdpi = dm.xdpi;
        ydpi = dm.ydpi;

        wm.getDefaultDisplay().getRealMetrics(dm);
        realWidthPixels = dm.widthPixels;
        realHeightPixels = dm.heightPixels;
    }

    public static int dp2px(float dp) {
        return (int) (dp * density + 0.5f);
    }

    public static int getWidthDp() {
        return (int) (widthPixels / density);
    }

    public static int getHeightDp() {
        return (int) (heightPixels / density);
    }

    public static int getWidthPixels() {
        return widthPixels;
    }

    public static int getHeightPixels() {
        return heightPixels;
    }

    public static int getRealWidthPixels() {
        return realWidthPixels;
    }

    public static int getRealHeightPixels() {
        return realHeightPixels;
    }

    public static float getDensity() {
        return density;
    }

    public static int getDensityDpi() {
        return densityDpi;
    }

    public static float getScaledDensity() {
        return scaledDensity;
    }

    public static float getXdpi() {
        return xdpi;
    }

    public static float getYdpi() {
        return ydpi;
    }

    public static String getDescription() {
        return "DisplayInfo{" +
                "widthPixels=" + widthPixels +
                ", heightPixels=" + heightPixels +
                ", realWidthPixels=" + realWidthPixels +
                ", realHeightPixels=" + realHeightPixels +
                ", density=" + density +
                ", densityDpi=" + densityDpi +
                ", scaledDensity=" + scaledDensity +
                ", xdpi=" + xdpi +
                ", ydpi=" + ydpi +
                '}';
    }

}
