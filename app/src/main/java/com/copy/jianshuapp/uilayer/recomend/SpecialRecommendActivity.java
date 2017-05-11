package com.copy.jianshuapp.uilayer.recomend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.copy.jianshuapp.uilayer.base.BaseActivity;

/**
 * 特别推荐
 * @version imkarl 2017-05
 */
public class SpecialRecommendActivity extends BaseActivity {

    public static void launch() {
        startActivity(SpecialRecommendActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("SpecialRecommendActivity");
        setContentView(button);
    }

}
