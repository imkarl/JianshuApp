package com.copy.jianshuapp.uilayer.views.listeners;

import android.view.View;

import com.badoo.mobile.util.WeakHandler;

/**
 * 支持单双击的点击事件监听器
 * @author alafighting 2016-01
 */
public abstract class OnMultiClickListener implements View.OnClickListener {

    private static final int DelayedTime = 250;
    private boolean isDouble = false;
    private View view;
    private WeakHandler handler = new WeakHandler();

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            isDouble = false;
            handler.removeCallbacks(this);
            onSingleClick(view);
        }
    };
    @Override
    public final void onClick(View v) {
        onGlobalClick(v);

        this.view = v;
        if (isDouble) {
            isDouble = false;
            handler.removeCallbacks(runnable);
            onDoubleClick(v);
        } else {
            isDouble = true;
            handler.postDelayed(runnable, DelayedTime);
        }
    }


    public void onGlobalClick(View view) {
    }

    public void onSingleClick(View view) {}

    public void onDoubleClick(View view) {}

}
