package com.copy.jianshuapp.uilayer.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.copy.jianshuapp.R;

/**
 * @version JianShu 2017-04
 */
@Deprecated
public class JSSwipeRefreshLayout extends SwipeRefreshLayout {
    private static final int DELAY_TIME = 300;
    private View targetView;

    public JSSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public JSSwipeRefreshLayout(Context context) {
        super(context);
        init();
    }

    public void show() {
        if (isVisible(this)) {
            setRefreshing(true);
        } else {
            postDelayed(() -> setRefreshing(true), DELAY_TIME);
        }
    }

    public void hide() {
        postDelayed(() -> setRefreshing(false), DELAY_TIME);
    }

    public void switchTheme() {
        init();
    }

    public void setNestSubView(View subView) {
        this.targetView = subView;
    }

    public boolean canChildScrollUp() {
        boolean z = false;
        if (this.targetView == null) {
            return super.canChildScrollUp();
        }
        return ViewCompat.canScrollVertically(this.targetView, -1);
    }

    public void init() {
        setColorSchemeResources(R.color.theme_color, R.color.theme_color, R.color.theme_color, R.color.theme_color);
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.swipe_refresh, typedValue, true);
        setProgressBackgroundColorSchemeResource(typedValue.resourceId);
    }

    private static boolean isVisible(View v) {
        return v != null && v.getMeasuredHeight() > 0 && v.getMeasuredWidth() > 0;
    }

}
