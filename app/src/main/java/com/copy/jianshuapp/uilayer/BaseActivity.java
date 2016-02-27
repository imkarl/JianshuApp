package com.copy.jianshuapp.uilayer;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.copy.jianshuapp.utils.version.AndroidVersion;

/**
 * Activity基类
 * @author alafighting 2016-01-19
 */
public class BaseActivity extends AppCompatActivity {

    public Context getContext() {
        return super.getApplicationContext();
    }

    public Activity getActivity() {
        return this;
    }

    public <T extends android.support.v4.app.Fragment> T findSupportFragment(Class<T> fragmentClass) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.Fragment fragment = fragmentManager.findFragmentByTag(fragmentClass.getName());
        return (T) fragment;
    }

    public <T extends android.support.v4.app.Fragment> T newSupportFragment(Class<T> fragmentClass, Bundle args) {
        android.support.v4.app.Fragment fragment = android.support.v4.app.Fragment.instantiate(getContext(), fragmentClass.getName(), args);
        return (T) fragment;
    }


}
