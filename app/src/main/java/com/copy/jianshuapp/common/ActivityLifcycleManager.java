package com.copy.jianshuapp.common;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.Stack;

/**
 * Activity生命周期管理
 * @author imkarl 2016-10
 */
public class ActivityLifcycleManager extends Stack<Activity> implements Application.ActivityLifecycleCallbacks {

    private static final ActivityLifcycleManager INSTANCE = new ActivityLifcycleManager();
    public static ActivityLifcycleManager get() {
        return INSTANCE;
    }


    // 当前应用程序是否在前台
    private boolean isForeground;

    private ActivityLifcycleManager() {
    }

    /**
     * 判断当前应用程序是否在前台
     * @return true:在前台
     */
    public boolean isForeground(){
        return isForeground;
    }

    /**
     * 获取当前顶层的Activity（堆栈中最后一个压入的）
     */
    public Activity current() {
        if (isEmpty()) {
            return null;
        }
        return super.lastElement();
    }

    /**
     * 结束所有Activity
     */
    public void finishAll(){
        for (Activity activity : this) {
            activity.finish();
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        super.add(activity);
    }
    @Override
    public void onActivityStarted(Activity activity) {
    }
    @Override
    public void onActivityResumed(Activity activity) {
        isForeground = true;
    }
    @Override
    public void onActivityPaused(Activity activity) {
        isForeground = false;
    }
    @Override
    public void onActivityStopped(Activity activity) {
    }
    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }
    @Override
    public void onActivityDestroyed(Activity activity) {
        super.remove(activity);
    }

}
