package com.copy.jianshuapp.uilayer.article.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.copy.jianshuapp.uilayer.base.BaseActivity;

/**
 * 文章详情
 * @version imkarl 2017-05
 */
public class ArticleDetailActivity extends BaseActivity {

    public static void launch(int id) {
        startActivity(ArticleDetailActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("ArticleDetailActivity");
        setContentView(button);
    }

}
