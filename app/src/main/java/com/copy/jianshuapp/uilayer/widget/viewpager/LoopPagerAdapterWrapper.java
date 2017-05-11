package com.copy.jianshuapp.uilayer.widget.viewpager;

import android.database.DataSetObserver;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * 无限循环Adapter
 * @version imkarl 2016-06
 */
class LoopPagerAdapterWrapper extends PagerAdapter {
    private PagerAdapter mAdapter;

    private SparseArray<ToDestroy> mToDestroy = new SparseArray<>();

    private boolean mBoundaryCaching = false;
    private boolean mCanLoop = true;

    public LoopPagerAdapterWrapper(PagerAdapter adapter) {
        this.mAdapter = adapter;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {
        super.registerDataSetObserver(observer);
        mAdapter.registerDataSetObserver(observer);
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        super.unregisterDataSetObserver(observer);
        mAdapter.unregisterDataSetObserver(observer);
    }

    @Override
    public void notifyDataSetChanged() {
        mToDestroy = new SparseArray<>();
        super.notifyDataSetChanged();
    }

    public int getInnerPosition(int realPosition) {
        return mCanLoop ? realPosition + 1 : realPosition;
    }

    private int getRealFirstPosition() {
        return mCanLoop ? 1 : 0;
    }

    private int getRealLastPosition() {
        return getRealFirstPosition() + getRealCount() - 1;
    }

    public int getRealPosition(int position) {
        int realCount = getRealCount();
        if (realCount == 0)
            return 0;
        int realPosition = (position-1) % realCount;
        if (realPosition < 0)
            realPosition += realCount;

        return realPosition;
    }

    private int getRealPositionByNofragment(int position) {
        int realPosition = getRealPosition(position);
        if (mAdapter instanceof android.support.v4.app.FragmentPagerAdapter
                || mAdapter instanceof android.support.v4.app.FragmentStatePagerAdapter
                || mAdapter instanceof android.support.v13.app.FragmentPagerAdapter
                || mAdapter instanceof android.support.v13.app.FragmentStatePagerAdapter) {
            realPosition = position;
        }
        return realPosition;
    }

    /**
     * 是否缓存边界
     */
    public void setBoundaryCaching(boolean flag) {
        mBoundaryCaching = flag;
    }

    public boolean isBoundaryCaching() {
        return mBoundaryCaching;
    }

    /**
     * 是否可以无限循环
     */
    public void setCanLoop(boolean canLoop) {
        this.mCanLoop = canLoop;
    }

    public boolean isCanLoop() {
        return mCanLoop;
    }

    @Override
    public int getCount() {
        if (getRealCount() <= 0) {
            return 0;
        }
        return mCanLoop ? getRealCount() + 2 : getRealCount();
    }

    public int getRealCount() {
        if (mAdapter == null) {
            return 0;
        }
        return mAdapter.getCount();
    }

    public PagerAdapter getRealAdapter() {
        return mAdapter;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        int realPosition = getRealPositionByNofragment(position);

        if (mBoundaryCaching) {
            ToDestroy toDestroy = mToDestroy.get(position);
            if (toDestroy != null) {
                mToDestroy.remove(position);
                return toDestroy.object;
            }
        }
        return mAdapter.instantiateItem(container, realPosition);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        int realFirst = getRealFirstPosition();
        int realLast = getRealLastPosition();
        int realPosition = getRealPositionByNofragment(position);

        if (mBoundaryCaching && (position == realFirst || position == realLast)) {
            mToDestroy.put(position, new ToDestroy(container, realPosition, object));
        } else {
            mAdapter.destroyItem(container, realPosition, object);
        }
    }

    /*
     * Delegate rest of methods directly to the inner adapter.
     */

    @Override
    public void finishUpdate(ViewGroup container) {
        mAdapter.finishUpdate(container);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return mAdapter.isViewFromObject(view, object);
    }

    @Override
    public void restoreState(Parcelable bundle, ClassLoader classLoader) {
        mAdapter.restoreState(bundle, classLoader);
    }

    @Override
    public Parcelable saveState() {
        return mAdapter.saveState();
    }

    @Override
    public void startUpdate(ViewGroup container) {
        mAdapter.startUpdate(container);
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        mAdapter.setPrimaryItem(container, position, object);
    }

    /*
     * End delegation
     */

    /**
     * Container class for caching the boundary views
     */
    private static class ToDestroy {
        ViewGroup container;
        int position;
        Object object;

        ToDestroy(ViewGroup container, int position, Object object) {
            this.container = container;
            this.position = position;
            this.object = object;
        }
    }
}
