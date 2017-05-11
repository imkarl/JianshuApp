package com.copy.jianshuapp.uilayer.notify;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.copy.jianshuapp.uilayer.base.BaseActivity;
import com.copy.jianshuapp.uilayer.enums.NotificationType;
import com.copy.jianshuapp.utils.pair.KeyValuePair;

/**
 * 通知
 * @version imkarl 2017-05
 */
public class NotifyDetailActivity extends BaseActivity {
    private static final String EXTRA_TYPE = "extra_type";

    public static void launch(NotificationType type) {
        startActivity(NotifyDetailActivity.class, new KeyValuePair<>(EXTRA_TYPE, type));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("NotifyDetailActivity");
        setContentView(button);
    }

}
