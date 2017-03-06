package com.copy.jianshuapp.common;

import android.content.Context;

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
    public static void onCreate(JSApplication app) {
        sContext = app;
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

}
