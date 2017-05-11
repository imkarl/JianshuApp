package com.copy.jianshuapp.uilayer.chat;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Button;

import com.copy.jianshuapp.uilayer.base.BaseActivity;
import com.copy.jianshuapp.uilayer.enums.NotificationType;
import com.copy.jianshuapp.utils.pair.KeyValuePair;

/**
 * 聊天列表\简信
 * @version imkarl 2017-05
 */
public class ChatListActivity extends BaseActivity {

    public static void launch() {
        startActivity(ChatListActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Button button = new Button(this);
        button.setText("ChatListActivity");
        setContentView(button);
    }

}
