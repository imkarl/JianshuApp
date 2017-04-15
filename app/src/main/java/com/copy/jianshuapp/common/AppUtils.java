package com.copy.jianshuapp.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import com.copy.jianshuapp.JSApplication;
import com.copy.jianshuapp.modellayer.local.db.AccountDao;
import com.copy.jianshuapp.modellayer.local.db.model.Account;

/**
 * App通用工具类
 * @version imkarl 2017-03
 */
public class AppUtils {

    private static final Account NO_LOGIN = new Account();

    private static float SCREEN_DENSITY = 1;
    private static int SCREEN_WIDTH_PIXELS;
    private static int SCREEN_HEIGHT_PIXELS;
    private static int SCREEN_WIDTH_DP;
    private static int SCREEN_HEIGHT_DP;

    private static Account sLoginAccount;
    private static JSApplication sContext;

    public static void attachBaseContext(JSApplication app) {
        sContext = app;
        VersionChannel.attachBaseContext(app);

        // 屏幕信息
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) sContext.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        SCREEN_DENSITY = dm.density;
        SCREEN_WIDTH_PIXELS = dm.widthPixels;
        SCREEN_HEIGHT_PIXELS = dm.heightPixels;
        SCREEN_WIDTH_DP = (int) (SCREEN_WIDTH_PIXELS / dm.density);
        SCREEN_HEIGHT_DP = (int) (SCREEN_HEIGHT_PIXELS / dm.density);
    }
    public static void onCreate() {
        VersionChannel.onCreate();
    }

    public static Context getContext() {
        return sContext;
    }
    public static JSApplication getApplication() {
        return sContext;
    }

    public static Account getLoginAccount() {
        if (sLoginAccount == null) {
            synchronized (AccountDao.class) {
                if (sLoginAccount == null) {
                    Account account = AccountDao.getLoginAccount();
                    if (account == null) {
                        account = NO_LOGIN;
                    }
                    sLoginAccount = account;
                }
            }
        }
        return sLoginAccount;
    }
    public static void setLoginAccount(Account account) {
        if (account == null) {
            account = NO_LOGIN;
        }
        sLoginAccount = account;
    }
    public static boolean isLogin() {
        return getLoginAccount() != NO_LOGIN;
    }


    /**
     * 获取App信息
     */
    public static AppInfo getAppInfo(Context context) {
        PackageManager pm = context.getPackageManager();
        PackageInfo pi = null;
        try {
            pi = pm.getPackageInfo(context.getApplicationContext().getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.w(e);
        }
        return getBean(pm, pi);
    }
    private static AppInfo getBean(PackageManager pm, PackageInfo pi) {
        if (pm == null || pi == null) return null;

        ApplicationInfo ai = pi.applicationInfo;
        String name = ai.loadLabel(pm).toString();
        Drawable icon = ai.loadIcon(pm);
        String packageName = pi.packageName;
        String packagePath = ai.sourceDir;
        String versionName = pi.versionName;
        int versionCode = pi.versionCode;
        boolean isSystem = (ApplicationInfo.FLAG_SYSTEM & ai.flags) != 0;
        return new AppInfo(name, icon, packageName, packagePath, versionName, versionCode, isSystem);
    }

    /**
     * 检测网络是否可用
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }

    public static int dp2px(float dp) {
        final float scale = SCREEN_DENSITY;
        return (int) (dp * scale + 0.5f);
    }

}
