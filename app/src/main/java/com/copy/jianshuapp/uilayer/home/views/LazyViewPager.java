package com.copy.jianshuapp.uilayer.home.views;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.badoo.mobile.util.WeakHandler;
import com.copy.jianshuapp.common.LogUtils;

import java.util.List;

/**
 * 支持禁止预加载的ViewPager
 *
 * @author alafighting 2016-01
 */
public class LazyViewPager extends ViewPager {

    private ViewPager.OnPageChangeListener deliverPageChangeListener;
    private FragmentManager fragmentManager;
    private WeakHandler handler;
    private boolean isInit = false;
    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        int max_repeate_count = 4;
        int max_repeate_count2 = 4;
        int repeate_count = 0;
        int repeate_count2 = 0;

        private void childOnPageSelectedLoop(final int paramAnonymousInt) {
            PagerAdapter localObject = getAdapter();

            if (localObject != null) {
                Fragment fragment = (Fragment) (localObject.instantiateItem(LazyViewPager.this, paramAnonymousInt));
                LogUtils.e("onPageSelected : " + (fragment.isAdded() + " " + fragment.getActivity() + " " + localObject));
                if ((localObject instanceof LazyViewPager.OnPageSelectedListener)) {
                    if (fragment.isAdded()) {
                        if (this.repeate_count2 > this.max_repeate_count2) {
                            return;
                        }

                        handler.postDelayed(new Runnable() {
                            public void run() {
                                if ((LazyViewPager.this.parentFragment == null) || (!LazyViewPager.this.parentFragment.isAdded()) || (LazyViewPager.this.parentFragment.isDetached())) {
                                    return;
                                }
                                LogUtils.e(" delayed childOnPageSelectedLoop : " + paramAnonymousInt + "  "
                                        + repeate_count2 + "  "
                                        + parentFragment.isDetached() + "  "
                                        + parentFragment.isAdded() + "  "
                                        + parentFragment);
                                childOnPageSelectedLoop(paramAnonymousInt);
                            }
                        }, 500L);
                        this.repeate_count2 += 1;
                    }
                    ((LazyViewPager.OnPageSelectedListener) localObject).onPageSelected(paramAnonymousInt);
                }
            }
        }

        private void onPageSelectedLoop(final int paramAnonymousInt) {
            List<Fragment> localList = LazyViewPager.this.fragmentManager.getFragments();
            LogUtils.e(" fragmentList : " + localList);
            if (localList == null) {
                if (this.repeate_count > this.max_repeate_count) {
                    return;
                }
                if (LazyViewPager.this.handler == null) {
                    // TODO
                    LogUtils.e("access$202...");
//                    LazyViewPager.access$202(LazyViewPager.this, new WeakHandler());
                }
                LazyViewPager.this.handler.postDelayed(new Runnable() {
                    public void run() {
                        if ((LazyViewPager.this.parentFragment == null) || (!LazyViewPager.this.parentFragment.isAdded()) || (LazyViewPager.this.parentFragment.isDetached())) {
                            return;
                        }
                        LogUtils.e(" delayed onPageSelectedLoop : " + paramAnonymousInt + "  " + repeate_count);
                        onPageSelectedLoop(paramAnonymousInt);
                    }
                }, 500L);
                this.repeate_count += 1;
                return;
            }
            childOnPageSelectedLoop(paramAnonymousInt);
        }

        public void onPageScrollStateChanged(int paramAnonymousInt) {
            if (LazyViewPager.this.deliverPageChangeListener != null) {
                LazyViewPager.this.deliverPageChangeListener.onPageScrollStateChanged(paramAnonymousInt);
            }
        }

        public void onPageScrolled(int paramAnonymousInt1, float paramAnonymousFloat, int paramAnonymousInt2) {
            if (LazyViewPager.this.deliverPageChangeListener != null) {
                LazyViewPager.this.deliverPageChangeListener.onPageScrolled(paramAnonymousInt1, paramAnonymousFloat, paramAnonymousInt2);
            }
        }

        public void onPageSelected(int paramAnonymousInt) {
            LogUtils.e("onPageSelected : " + paramAnonymousInt);
            if (LazyViewPager.this.deliverPageChangeListener != null) {
                LazyViewPager.this.deliverPageChangeListener.onPageSelected(paramAnonymousInt);
            }
            onPageSelectedLoop(paramAnonymousInt);
        }
    };
    private Fragment parentFragment;

    public LazyViewPager(Context paramContext) {
        super(paramContext);
    }

    public LazyViewPager(Context paramContext, AttributeSet paramAttributeSet) {
        super(paramContext, paramAttributeSet);
    }

    public void init(FragmentManager paramp, Fragment paramk) {
        this.fragmentManager = paramp;
        this.parentFragment = paramk;
        setOnPageChangeListener(this.onPageChangeListener);
        this.isInit = true;
    }

    public void setFirstPositionCurrentItem() {
        if (getCurrentItem() == 0) {
            LogUtils.e("setCurrentItem onPageSelected custom ");
            this.onPageChangeListener.onPageSelected(0);
        }
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener paramf) {
        if (this.isInit) {
            LogUtils.e("111111111" + paramf + "     " + this);
            this.deliverPageChangeListener = paramf;
            return;
        }
        LogUtils.e("2222222222" + paramf + "     " + this);
        super.setOnPageChangeListener(paramf);
    }

    public static abstract interface OnPageSelectedListener {
        public abstract void onPageSelected(int paramInt);
    }

}
