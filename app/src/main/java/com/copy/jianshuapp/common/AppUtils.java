package com.copy.jianshuapp.common;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.copy.jianshuapp.JSApplication;
import com.copy.jianshuapp.modellayer.local.db.AccountDao;
import com.copy.jianshuapp.modellayer.local.db.model.Account;

/**
 * App通用工具类
 * @version imkarl 2017-03
 */
public class AppUtils {

    private static final Account NO_LOGIN = new Account();

    private static Account sLoginAccount;
    private static JSApplication sContext;

    public static void attachBaseContext(JSApplication app) {
        sContext = app;
        VersionChannel.attachBaseContext(app);
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
     * 检测网络是否可用
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }

}
