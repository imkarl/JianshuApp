package com.copy.jianshuapp.uilayer.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.copy.jianshuapp.R;
import com.copy.jianshuapp.common.AppUtils;
import com.copy.jianshuapp.uilayer.base.BaseActivity;
import com.copy.jianshuapp.uilayer.login.fragment.LoginFragment;
import com.copy.jianshuapp.uilayer.login.fragment.RegisterFragment;

/**
 * 用户登录
 * @version imkarl 2017-03
 */
public class LoginActivity extends BaseActivity {
    private static final String EXTRA_TYPE = "extra_login_type";

    private static final int TYPE_LOGIN = 1;
    private static final int TYPE_REGISTER = 2;

    public static Intent launchLogin() {
        Intent intent = new Intent(AppUtils.getContext(), LoginActivity.class);
        intent.putExtra(EXTRA_TYPE, TYPE_LOGIN);
        return intent;
    }
    public static Intent launchRegister() {
        Intent intent = new Intent(AppUtils.getContext(), LoginActivity.class);
        intent.putExtra(EXTRA_TYPE, TYPE_REGISTER);
        return intent;
    }

    private int mLoginType;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.container);

        Intent intent = getIntent();
        if (intent != null) {
            this.mLoginType = intent.getIntExtra(EXTRA_TYPE, TYPE_LOGIN);
        }

        if (this.mLoginType == TYPE_REGISTER) {
            showRegisterFragment();
        } else {
            showLoginFragment();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_TYPE, this.mLoginType);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        this.mLoginType = savedInstanceState.getInt(EXTRA_TYPE, TYPE_LOGIN);
    }

    private void showRegisterFragment() {
        beginTransaction()
                .replace(R.id.frame_root, RegisterFragment.class)
                .commit();
    }
    private void showLoginFragment() {
        beginTransaction()
                .replace(R.id.frame_root, LoginFragment.class)
                .commit();
    }

}
