package com.copy.jianshuapp.uilayer.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.copy.jianshuapp.common.AppUtils;
import com.copy.jianshuapp.uilayer.base.BaseActivity;

/**
 * 用户登录
 * @version imkarl 2017-03
 */
public class LoginActivity extends BaseActivity {

    public static Intent launchLogin() {
        return new Intent(AppUtils.getContext(), LoginActivity.class);
    }
    public static Intent launchRegister() {
        return new Intent(AppUtils.getContext(), LoginActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button button = new Button(this);
        button.setText("login");
        setContentView(button);
    }
}
