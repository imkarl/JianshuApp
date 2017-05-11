package com.copy.jianshuapp.uilayer.widget.viewpager;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewConfiguration;

import com.copy.jianshuapp.uilayer.listeners.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

/**
 * 无限循环ViewPager
 * @version imkarl 2016-06
 */
public class LoopViewPager extends FixViewPager {
    public static final boolean DEFAULT_BOUNDARY_CACHING = false;

    private List<OnPageChangeListener> mOnPageChangeListeners;
    private OnPageChangeListener mOnPageChangeListener;
    LoopPagerAdapterWrapper mAdapter;

    private boolean isCanScroll = true;
    private OnItemClickListener<Integer> mOnItemClickListener;

    private OnPageChangeListener mInternalPageChangeListener = new OnPageChangeListener() {
        private float mPreviousOffset = -1;
        private float mPreviousPosition = -1;

        @Override
        public void onPageSelected(int position) {
            int realPosition = mAdapter.getRealPosition(position);

            if (mPreviousPosition != realPosition) {
                mPreviousPosition = realPosition;
                dispatchOnPageSelected(realPosition);
            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset,
                                   int positionOffsetPixels) {
            int realPosition = position;

            if (mAdapter != null) {
                realPosition = mAdapter.getRealPosition(position);
                if (positionOffset == 0
                        && mPreviousOffset == 0
                        && (position == 0 || position == mAdapter.getCount() - 1)) {
                    setCurrentItem(realPosition, false);
                }
            }
            mPreviousOffset = positionOffset;

            dispatchOnPageScrolled(realPosition, positionOffset, positionOffsetPixels);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            dispatchOnPageScrollStateChanged(state);
        }
    };


    public LoopViewPager(Context context) {
        super(context);
        init();
    }
    public LoopViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        super.addOnPageChangeListener(mInternalPageChangeListener);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public boolean isCanScroll() {
        return isCanScroll;
    }

    public void setCanScroll(boolean isCanScroll) {
        this.isCanScroll = isCanScroll;
    }

    private float oldX = 0;
    private final float TOUCH_SLOP = ViewConfiguration.get(getContext()).getScaledTouchSlop();

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isCanScroll) {
            if (mOnItemClickListener != null) {
                switch (ev.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        oldX = ev.getX();
                        break;

                    case MotionEvent.ACTION_UP:
                        float newX = ev.getX();
                        if (Math.abs(oldX - newX) < TOUCH_SLOP) {
                            mOnItemClickListener.onItemClick(getCurrentItem());
                        }
                        oldX = 0;
                        break;
                }
            }
            if (mAdapter == null || mAdapter.getCount() == 0) {
                return false;
            }
            try {
                return super.onTouchEvent(ev);
            } catch (IllegalStateException e) {
                mAdapter.notifyDataSetChanged();
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return isCanScroll && super.onInterceptTouchEvent(ev);
    }

    public boolean isCanLoop() {
        return mAdapter != null && mAdapter.isCanLoop();
    }

    public void setCanLoop(boolean canLoop) {
        if (mAdapter == null) {
            return;
        }
        mAdapter.setCanLoop(canLoop);
        mAdapter.notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener<Integer> listener) {
        this.mOnItemClickListener = listener;
    }


    @Override
    public void setAdapter(PagerAdapter adapter) {
        if (adapter instanceof LoopPagerAdapterWrapper) {
            mAdapter = (LoopPagerAdapterWrapper) adapter;
        } else {
            mAdapter = new LoopPagerAdapterWrapper(adapter);
        }
        mAdapter.setBoundaryCaching(DEFAULT_BOUNDARY_CACHING);
        super.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        setCurrentItem(0, false);
    }

    @Override
    public PagerAdapter getAdapter() {
        return mAdapter != null ? mAdapter.getRealAdapter() : null;
    }

    @Override
    public int getCurrentItem() {
        return mAdapter != null ? mAdapter.getRealPosition(super.getCurrentItem()) : 0;
    }

    @Override
    public void setCurrentItem(int item, boolean smoothScroll) {
        int realItem = mAdapter.getInnerPosition(item);
        super.setCurrentItem(realItem, smoothScroll);
    }
    @Override
    public void setCurrentItem(int item) {
        if (getCurrentItem() != item) {
            setCurrentItem(item, true);
        }
    }

    @Deprecated
    @Override
    public void setOnPageChangeListener(OnPageChangeListener listener) {
        mOnPageChangeListener = listener;
    }

    @Override
    public void addOnPageChangeListener(OnPageChangeListener listener) {
        if (mOnPageChangeListeners == null) {
            mOnPageChangeListeners = new ArrayList<>();
        }
        mOnPageChangeListeners.add(listener);
    }
    @Override
    public void removeOnPageChangeListener(OnPageChangeListener listener) {
        if (mOnPageChangeListeners != null) {
            mOnPageChangeListeners.remove(listener);
        }
    }
    @Override
    public void clearOnPageChangeListeners() {
        if (mOnPageChangeListeners != null) {
            mOnPageChangeListeners.clear();
        }
    }



    void dispatchOnPageScrolled(int position, float offset, int offsetPixels) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrolled(position, offset, offsetPixels);
        }
        if (mOnPageChangeListeners != null) {
            for (int i = 0, z = mOnPageChangeListeners.size(); i < z; i++) {
                OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                if (listener != null) {
                    listener.onPageScrolled(position, offset, offsetPixels);
                }
            }
        }
    }
    void dispatchOnPageSelected(int position) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageSelected(position);
        }
        if (mOnPageChangeListeners != null) {
            for (int i = 0, z = mOnPageChangeListeners.size(); i < z; i++) {
                OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                if (listener != null) {
                    listener.onPageSelected(position);
                }
            }
        }
    }
    void dispatchOnPageScrollStateChanged(int state) {
        if (mOnPageChangeListener != null) {
            mOnPageChangeListener.onPageScrollStateChanged(state);
        }
        if (mOnPageChangeListeners != null) {
            for (int i = 0, z = mOnPageChangeListeners.size(); i < z; i++) {
                OnPageChangeListener listener = mOnPageChangeListeners.get(i);
                if (listener != null) {
                    listener.onPageScrollStateChanged(state);
                }
            }
        }
    }

}
