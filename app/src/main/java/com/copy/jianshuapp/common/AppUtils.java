package com.copy.jianshuapp.common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;

import com.copy.jianshuapp.JSApplication;
import com.copy.jianshuapp.modellayer.local.db.AccountDao;
import com.copy.jianshuapp.modellayer.model.Account;

/**
 * App通用工具类
 * @version imkarl 2017-03
 */
public class AppUtils {

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
                    sLoginAccount = AccountDao.readLoginAccount();
                }
            }
        }
        return sLoginAccount;
    }
    public static void setLoginAccount(Account account) {
        if (account != null) {
            AccountDao.saveLoginAccount(account);
        }
        sLoginAccount = account;
    }
    public static boolean isLogin() {
        return getLoginAccount() != null;
    }

    /**
     * 检测网络是否可用
     */
    public static boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }

    /**
     * 当前线程是否为主线程
     */
    public static boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

}
