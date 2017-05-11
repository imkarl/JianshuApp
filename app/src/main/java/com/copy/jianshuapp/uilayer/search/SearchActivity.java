package com.copy.jianshuapp.uilayer.search;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.copy.jianshuapp.uilayer.base.BaseActivity;
import com.copy.jianshuapp.utils.pair.KeyValuePair;

/**
 * 搜索
 * @version imkarl 2017-05
 */
public class SearchActivity extends BaseActivity {
    private static final String EXTRA_IS_FROM_HOME = "is_from_home";

    public static void launch() {
        startActivity(SearchActivity.class);
    }
    public static void lanuchFromHome() {
        startActivity(SearchActivity.class, new KeyValuePair<>(EXTRA_IS_FROM_HOME, true));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("SearchActivity");
        setContentView(button);
    }

}
