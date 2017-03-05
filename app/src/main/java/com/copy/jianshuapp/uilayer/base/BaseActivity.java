package com.copy.jianshuapp.uilayer.base;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import com.copy.jianshuapp.common.statusbar.StatusBarCompat;
import com.copy.jianshuapp.utils.pair.KeyValuePair;

/**
 * Activity基类
 * @author alafighting 2016-01-19
 */
public class BaseActivity extends RxLifecycleActivity {

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
    public final void startActivity(Class<? extends Activity> targetActivity, KeyValuePair<String, String>... params) {
        Intent intent = new Intent(this, targetActivity);
        if (params != null && params.length > 0) {
            for (KeyValuePair<String, String> pair : params) {
                intent.putExtra(pair.getKey(), pair.getValue());
            }
        }
        this.startActivity(intent);
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

    @Override
    public void onContentChanged() {
        super.onContentChanged();

        // 设置状态栏风格
        StatusBarCompat.setStatusBarColor(getWindow(), Color.WHITE);
        StatusBarCompat.setStatusBarLight(getWindow(), true);
    }

}
