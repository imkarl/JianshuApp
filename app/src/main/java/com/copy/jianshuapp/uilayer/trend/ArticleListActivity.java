package com.copy.jianshuapp.uilayer.trend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.copy.jianshuapp.modellayer.model.TrendCategory;
import com.copy.jianshuapp.uilayer.base.BaseActivity;
import com.copy.jianshuapp.utils.pair.KeyValuePair;

/**
 * 文章列表
 * @version imkarl 2017-05
 */
public class ArticleListActivity extends BaseActivity {
    private static final String EXTRA_ENTITY = "extra_entity";

    public static void launch(TrendCategory category) {
        startActivity(ArticleListActivity.class, new KeyValuePair<>(EXTRA_ENTITY, category));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("ArticleListActivity");
        setContentView(button);
    }

}
