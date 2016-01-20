package com.copy.jianshuapp.uilayer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.copy.jianshuapp.uilayer.activitys.MainActivity;
import com.copy.jianshuapp.utils.pair.KeyValuePair;

/**
 * UI操作辅助类
 * @author alafighting 2016-0
 */
public class UIHelper {

    /**
     * 界面跳转
     * @param context Context
     * @param activity 目标Activity
     * @param params Intent传参
     */
    public static void startActivity(Context context, Class<? extends Activity> activity, KeyValuePair<String, String>... params) {
        if (context == null) {
            throw new NullPointerException("context不能为空");
        }
        Intent intent = new Intent(context, activity);
        if (params != null && params.length > 0) {
            for (int i=0; i<params.length; i++) {
                KeyValuePair<String, String> pair = params[i];
                intent.putExtra(pair.getKey(), pair.getValue());
            }
        }
        if (context.equals(context.getApplicationContext())) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
    }


    /**
     * 打开主界面
     * @param context
     */
    public static void startMain(Context context) {
        startActivity(context, MainActivity.class);
    }

}
