package com.copy.jianshuapp.uilayer.editor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.copy.jianshuapp.uilayer.base.BaseActivity;

/**
 * 编辑
 * @version imkarl 2017-05
 */
public class EditorActivity extends BaseActivity {

    public static void launch() {
        startActivity(EditorActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("EditorActivity");
        setContentView(button);
    }

}
