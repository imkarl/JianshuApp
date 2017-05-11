package com.copy.jianshuapp.uilayer.friend.circle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.copy.jianshuapp.uilayer.base.BaseActivity;

/**
 * 简友圈
 * @version imkarl 2017-04
 */
public class FriendCircleActivity extends BaseActivity {

    public static void launch() {
        startActivity(FriendCircleActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("FriendCircleActivity");
        setContentView(button);
    }

}
