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
    private View targetView;
    private boolean mMeasured = false;
    private boolean mPreMeasureRefreshing = false;

    public JSSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public JSSwipeRefreshLayout(Context context) {
        super(context);
        init();
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

    @Override
    public void onMeasure(final int widthMeasureSpec, final int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (!mMeasured) {
            mMeasured = true;
            setRefreshing(mPreMeasureRefreshing);
        }
    }

    @Override
    public void setRefreshing(final boolean refreshing) {
        mPreMeasureRefreshing = refreshing;
        if (mMeasured) {
            super.setRefreshing(mPreMeasureRefreshing);
        }
    }

}
