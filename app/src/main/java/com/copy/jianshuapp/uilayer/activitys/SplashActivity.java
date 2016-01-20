package com.copy.jianshuapp.uilayer.activitys;

import android.os.Bundle;

import com.copy.jianshuapp.uilayer.BaseActivity;
import com.copy.jianshuapp.uilayer.UIHelper;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        UIHelper.startMain(getActivity());
        finish();
    }

}
