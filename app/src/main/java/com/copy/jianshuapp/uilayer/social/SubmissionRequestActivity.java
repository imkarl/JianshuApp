package com.copy.jianshuapp.uilayer.social;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.copy.jianshuapp.uilayer.base.BaseActivity;

/**
 * 投稿请求
 * @version imkarl 2017-05
 */
public class SubmissionRequestActivity extends BaseActivity {

    public static void launch() {
        startActivity(SubmissionRequestActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("SubmissionRequestActivity");
        setContentView(button);
    }

}
