package com.copy.jianshuapp.uilayer.base;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.copy.jianshuapp.R;
import com.copy.jianshuapp.common.ActivityLifcycleManager;
import com.copy.jianshuapp.common.BundleUtils;
import com.copy.jianshuapp.common.statusbar.StatusBarCompat;
import com.copy.jianshuapp.common.statusbar.StatusBarStyle;
import com.copy.jianshuapp.modellayer.local.SettingsUtils;
import com.copy.jianshuapp.uilayer.SplashActivity;
import com.copy.jianshuapp.uilayer.home.activitys.MainActivity;
import com.copy.jianshuapp.utils.Constants;
import com.copy.jianshuapp.utils.pair.KeyValuePair;

/**
 * Activity基类
 * @author alafighting 2016-01-19
 * @author imkarl 2017-03
 */
public class BaseActivity extends SwipebackActivity {

    private FragmentManagerHelper mFragmentManagerHelper = new FragmentManagerHelper(this);

    public Context getContext() {
        return super.getApplicationContext();
    }

    public Activity getActivity() {
        return this;
    }

    /**
     * 界面跳转
     * @param targetActivity 目标Activity
     * @param params Intent传参
     */
    @SafeVarargs
    public static void startActivity(Class<? extends Activity> targetActivity, KeyValuePair<String, ?>... params) {
        Context context = ActivityLifcycleManager.get().current();
        Intent intent = new Intent(context, targetActivity);
        Bundle bundle = BundleUtils.toBundle(params);
        intent.putExtras(bundle);
        context.startActivity(intent);
    }

    @SuppressWarnings("unchecked")
    public <T extends Fragment> T findFragment(Class<T> fragmentClass) {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentClass.getName());
        return (T) fragment;
    }

    @SuppressWarnings("unchecked")
    public <T extends Fragment> T newFragment(Class<T> fragmentClass, Bundle args) {
        Fragment fragment = Fragment.instantiate(getContext(), fragmentClass.getName(), args);
        return (T) fragment;
    }

    public FragmentManagerHelper getFragmentHelper() {
        return mFragmentManagerHelper;
    }
    public FragmentTransactionHelper beginTransaction() {
        return new FragmentTransactionHelper(mFragmentManagerHelper);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Constants.JSTheme theme = SettingsUtils.getTheme();
        switchTheme(theme);
        switchStatusBarStyle(theme);
    }

    private void switchTheme(Constants.JSTheme theme) {
        if (this instanceof SplashActivity || this instanceof MainActivity) {
            return;
        }

        if (theme == null) {
            theme = Constants.JSTheme.DAY;
        }
        switch (theme) {
            case DAY:
                setTheme(R.style.theme_day);
                break;
            case NIGHT:
                setTheme(R.style.theme_night);
                break;
        }
    }
    private void switchStatusBarStyle(Constants.JSTheme theme) {
        if (theme == null) {
            theme = Constants.JSTheme.DAY;
        }
        switch (theme) {
            case DAY:
                // 设置状态栏风格
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    StatusBarCompat.setTransparent(this);
                } else {
                    StatusBarCompat.setColorRes(this, R.color.white);
                }
                StatusBarCompat.setStyle(this, StatusBarStyle.Light);
                break;
            case NIGHT:
                // 设置状态栏风格
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    StatusBarCompat.setTransparent(this);
                } else {
                    StatusBarCompat.setColorRes(this, R.color.bg_black_light);
                }
                StatusBarCompat.setStyle(this, StatusBarStyle.Dark);
                break;
        }
    }

}
