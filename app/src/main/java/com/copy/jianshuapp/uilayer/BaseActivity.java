package com.copy.jianshuapp.uilayer;

import android.app.Activity;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;

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

}
