package com.copy.jianshuapp.uilayer.subject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.copy.jianshuapp.uilayer.base.BaseActivity;

/**
 * 专题
 * @version imkarl 2017-04
 */
public class CollectionActivity extends BaseActivity {

    public static void launch(String id, String identity, boolean hasUnread) {
        startActivity(CollectionActivity.class);
    }
    public static void launch(int id, String title) {
        startActivity(CollectionActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("CollectionActivity");
        setContentView(button);
    }

}
