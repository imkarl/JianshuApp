package com.copy.jianshuapp.uilayer.activitys;

import android.os.Bundle;

import com.copy.jianshuapp.uilayer.BaseActivity;
import com.copy.jianshuapp.uilayer.UIHelper;

import java.util.concurrent.TimeUnit;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rx.Observable.timer(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
            @Override
            public void call(Long o) {
                UIHelper.startMain(getActivity());
                finish();
            }
        });
    }

}
