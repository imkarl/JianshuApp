package com.copy.jianshuapp.uilayer.login.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.copy.jianshuapp.R;
import com.copy.jianshuapp.uilayer.base.BaseActivity;
import com.copy.jianshuapp.uilayer.home.activitys.MainActivity;
import com.copy.jianshuapp.uilayer.login.fragment.LoginFragment;
import com.copy.jianshuapp.uilayer.login.fragment.RegisterFragment;
import com.copy.jianshuapp.utils.pair.KeyValuePair;

/**
 * 用户登录
 * @version imkarl 2017-03
 */
public class LoginActivity extends BaseActivity {
    private static final String EXTRA_TYPE = "extra_login_type";

    private static final int TYPE_LOGIN = 1;
    private static final int TYPE_REGISTER = 2;

    public static void launchLogin() {
        startActivity(LoginActivity.class, new KeyValuePair<>(EXTRA_TYPE, TYPE_LOGIN));
    }
    public static void launchRegister() {
        startActivity(LoginActivity.class, new KeyValuePair<>(EXTRA_TYPE, TYPE_REGISTER));
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
        if (savedInstanceState != null) {
            this.mLoginType = savedInstanceState.getInt(EXTRA_TYPE);
        }

        if (this.mLoginType == TYPE_REGISTER) {
            showRegisterFragment();
        } else {
            showLoginFragment();
        }
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(EXTRA_TYPE, this.mLoginType);
    }

    @Override
    public void onBackPressed() {
        MainActivity.launch();
        super.onBackPressed();
    }

    public void showRegisterFragment() {
        beginTransaction()
                .add(R.id.frame_root, RegisterFragment.class)
                .hide(LoginFragment.class)
                .commit();
    }
    public void showLoginFragment() {
        beginTransaction()
                .add(R.id.frame_root, LoginFragment.class)
                .hide(RegisterFragment.class)
                .commit();
    }

}
