package com.copy.jianshuapp.uilayer.user;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.copy.jianshuapp.uilayer.base.BaseActivity;

/**
 * 用户推送详情
 * @version imkarl 2017-04
 */
public class UserPushingDetailActivity extends BaseActivity {

    public static void launch(String id, String name, boolean hasUnread) {
        startActivity(UserPushingDetailActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("UserPushingDetailActivity");
        setContentView(button);
    }

}
