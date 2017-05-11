package com.copy.jianshuapp.uilayer.widget.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

import java.lang.ref.WeakReference;
import java.lang.reflect.Field;

/**
 * 支持自动播放的ViewPager
 * @version imkarl 2016-06
 */
public class AutoViewPager extends LoopViewPager {

    /**
     * 翻页时间间隔
     */
    private int delayTime;
    /**
     * 是否自动播放
     */
    private boolean mAutoPlay;
    /**
     * 自动播放任务
     */
    private AutoTask mAutoTask;
    /**
     * 滚动器
     */
    private ViewPagerScroller mScroller;

    public AutoViewPager(Context context) {
        super(context);
        init();
    }

    public AutoViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        setScroller(new ViewPagerScroller(getContext()));
        mAutoTask = new AutoTask(this);
    }

    /***
     * 是否开启了自动播放
     */
    public boolean isAutoPlay() {
        return mAutoPlay;
    }

    /***
     * 开始自动翻页
     * @param delayTime 自动翻页时间
     */
    public void startPlay(int delayTime) {
        // 如果是正在翻页的话先停掉
        if (this.mAutoPlay) {
            stopPlay();
        }
        // 设置可以翻页并开启翻页
        this.mAutoPlay = true;
        this.delayTime = delayTime;
        postDelayed(mAutoTask, this.delayTime);
    }

    public void stopPlay() {
        mAutoPlay = false;
        removeCallbacks(mAutoTask);
    }

    /**
     * 设置Scroller
     */
    public void setScroller(ViewPagerScroller scroller) {
        try {
            Field scrollerField = ViewPager.class.getDeclaredField("mScroller");
            scrollerField.setAccessible(true);
            scrollerField.set(this, scroller);
            this.mScroller = scroller;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 触碰控件的时候，翻页应该停止，离开的时候如果之前是开启了翻页的话则重新启动翻页
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_CANCEL || action == MotionEvent.ACTION_OUTSIDE) {
            // 开始翻页
            if (mAutoPlay) startPlay(delayTime);
        } else if (action == MotionEvent.ACTION_DOWN) {
            // 停止翻页
            if (mAutoPlay) {
                stopPlay();
                mAutoPlay = true;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 设置ViewPager的滚动速度
     */
    public void setScrollDuration(int scrollDuration) {
        mScroller.setScrollDuration(scrollDuration);
    }

    public int getScrollDuration() {
        return mScroller.getScrollDuration();
    }


    private static class AutoTask implements Runnable {
        private final WeakReference<AutoViewPager> reference;

        AutoTask(AutoViewPager viewPager) {
            this.reference = new WeakReference<>(viewPager);
        }

        @Override
        public void run() {
            AutoViewPager viewPager = reference.get();

            if (viewPager != null) {
                if (viewPager.mAutoPlay) {
                    int pageNext = viewPager.getCurrentItem() + 1;

                    LoopPagerAdapterWrapper adapter = viewPager.mAdapter;
                    if (adapter != null && adapter.getRealCount() > 0) {
                        if (pageNext == adapter.getRealCount()) {
                            pageNext = 0;
                        }

                        try {
                            viewPager.setCurrentItem(pageNext);
                        } catch (IllegalStateException e) {
                            adapter.notifyDataSetChanged();
                        }
                    }

                    viewPager.postDelayed(viewPager.mAutoTask, viewPager.delayTime);
                }
            }
        }
    }

}
