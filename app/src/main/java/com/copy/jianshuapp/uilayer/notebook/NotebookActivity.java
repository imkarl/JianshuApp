package com.copy.jianshuapp.uilayer.notebook;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.copy.jianshuapp.uilayer.base.BaseActivity;

/**
 * 文集
 * @version imkarl 2017-04
 */
public class NotebookActivity extends BaseActivity {

    public static void launch(String id, String identity, boolean hasUnread) {
        startActivity(NotebookActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("NotebookActivity");
        setContentView(button);
    }

}
