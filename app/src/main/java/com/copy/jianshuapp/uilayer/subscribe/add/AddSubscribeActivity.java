package com.copy.jianshuapp.uilayer.subscribe.add;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.copy.jianshuapp.uilayer.base.BaseActivity;

/**
 * 添加关注
 * @version imkarl 2017-04
 */
public class AddSubscribeActivity extends BaseActivity {

    public static void launch() {
        startActivity(AddSubscribeActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("AddSubscribeActivity");
        setContentView(button);
    }

}
