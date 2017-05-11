package com.copy.jianshuapp.uilayer.recomend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.copy.jianshuapp.uilayer.base.BaseActivity;

/**
 * 推荐
 * @version imkarl 2017-05
 */
public class RecommendActivity extends BaseActivity {

    public static void launch() {
        startActivity(RecommendActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("RecommendActivity");
        setContentView(button);
    }

}
