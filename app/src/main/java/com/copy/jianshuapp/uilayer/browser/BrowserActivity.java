package com.copy.jianshuapp.uilayer.browser;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.copy.jianshuapp.uilayer.base.BaseActivity;
import com.copy.jianshuapp.utils.pair.KeyValuePair;

/**
 * 内置浏览器
 * @version imkarl 2017-03
 */
public class BrowserActivity extends BaseActivity {
    private static final String EXTRA_URL = "extra_url";

    public static void launch(String url) {
        startActivity(BrowserActivity.class, new KeyValuePair<>(EXTRA_URL, url));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("BrowserActivity");
        setContentView(button);
    }

}
