package com.copy.jianshuapp.uilayer.settings;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.copy.jianshuapp.uilayer.base.BaseActivity;

/**
 * 设置-通知
 * @version imkarl 2017-05
 */
public class SettingNotificationActivity extends BaseActivity {

    public static void launch() {
        startActivity(SettingNotificationActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("SettingNotificationActivity");
        setContentView(button);
    }

}
