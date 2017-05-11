package com.copy.jianshuapp.uilayer.widget.viewpager;

import android.content.Context;
import android.view.animation.Interpolator;
import android.widget.Scroller;

/**
 * 速度可控的Scroller
 * @version imkarl 2016-06
 */
class ViewPagerScroller extends Scroller {
    /**
     * 滑动耗时
     */
    private int mScrollDuration = 800;
    private boolean zero;

    public ViewPagerScroller(Context context) {
        super(context);
    }
    public ViewPagerScroller(Context context, Interpolator interpolator) {
        super(context, interpolator);
    }
    public ViewPagerScroller(Context context, Interpolator interpolator, boolean flywheel) {
        super(context, interpolator, flywheel);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy, int duration) {
        super.startScroll(startX, startY, dx, dy, zero ? 0 : mScrollDuration);
    }

    @Override
    public void startScroll(int startX, int startY, int dx, int dy) {
        super.startScroll(startX, startY, dx, dy, zero ? 0 : mScrollDuration);
    }

    public int getScrollDuration() {
        return mScrollDuration;
    }

    public void setScrollDuration(int scrollDuration) {
        this.mScrollDuration = scrollDuration;
    }

    public boolean isZero() {
        return zero;
    }

    public void setZero(boolean zero) {
        this.zero = zero;
    }
}
