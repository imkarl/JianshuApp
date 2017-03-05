package com.copy.jianshuapp.uilayer.guide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.copy.jianshuapp.uilayer.BaseActivity;

/**
 * 引导页
 * @version imkarl 2017-03
 */
public class GuideActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button textView = new Button(this);
        textView.setText("guide");
        setContentView(textView);

    }
}
