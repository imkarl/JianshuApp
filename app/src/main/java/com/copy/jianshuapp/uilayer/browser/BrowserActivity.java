package com.copy.jianshuapp.uilayer.browser;

import android.content.Intent;

import com.copy.jianshuapp.common.AppUtils;
import com.copy.jianshuapp.uilayer.base.BaseActivity;

/**
 * 内置浏览器
 * @version imkarl 2017-03
 */
public class BrowserActivity extends BaseActivity {
    private static final String EXTRA_URL = "extra_url";

    public static Intent launch(String url) {
        Intent intent = new Intent(AppUtils.getContext(), BrowserActivity.class);
        intent.putExtra(EXTRA_URL, url);
        return intent;
    }

}
