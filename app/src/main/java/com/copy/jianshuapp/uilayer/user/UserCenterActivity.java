package com.copy.jianshuapp.uilayer.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.copy.jianshuapp.uilayer.base.BaseActivity;

/**
 * 用户推送详情
 * @version imkarl 2017-05
 */
public class UserCenterActivity extends BaseActivity {

    public static void launch(int id) {
        startActivity(UserCenterActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("UserCenterActivity");
        setContentView(button);
    }

}
