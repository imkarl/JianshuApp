package com.copy.jianshuapp.uilayer.widget.adapter;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.copy.jianshuapp.R;
import com.copy.jianshuapp.common.AppUtils;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 方便快速开发的PagerAdapter基类
 * @version imkarl 2017-03
 */
public abstract class QuickPagerAdapter<T> extends PagerAdapter {
    @IdRes private static final int TAG_VIEW_HOLDER = R.id.global_tag;

    private LinkedList<View> recycledViews = new LinkedList<>();
    private Context mContext;
    private LayoutInflater mLayoutInflater;
    private int mFixCount = 0;

    @LayoutRes private final int layoutResId;
    private final List<T> data;

    public QuickPagerAdapter(@LayoutRes int layoutResId) {
        this(layoutResId, null);
    }
    public QuickPagerAdapter(@LayoutRes int layoutResId, List<T> data) {
        this.layoutResId = layoutResId;
        this.data = data==null ? new ArrayList<>() : new ArrayList<>(data);
    }

    public List<T> getData() {
        return data;
    }

    @Override
    public int getCount() {
        return data.size();
    }
    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public Object instantiateItem(ViewGroup container, int position) {
        this.mContext = container.getContext();
        this.mLayoutInflater = LayoutInflater.from(this.mContext);

        View itemView;
        ItemViewHelper helper;
        if (recycledViews != null && recycledViews. size() >0 ) {
            itemView = recycledViews.getFirst();
            recycledViews.removeFirst();
            helper = (ItemViewHelper) itemView.getTag(TAG_VIEW_HOLDER);
        } else {
            itemView = getItemView(container);
            helper = new ItemViewHelper(itemView);
            itemView.setTag(TAG_VIEW_HOLDER, helper);
        }
        container.addView(itemView);

        helper.setPosition(position);
        convert(helper, getItem(position));
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        if (object != null) {
            recycledViews.addLast((View)object);
        }
    }

    @Override
    public void notifyDataSetChanged() {
        mFixCount = getCount()+1;
        super.notifyDataSetChanged();
    }

    @Override
    public int getItemPosition(Object object) {
        // 修复动态更新item数量，不刷新的bug
        if (mFixCount > 0) {
            mFixCount--;
            return POSITION_NONE;
        }
        return super.getItemPosition(object);
    }

    public T getItem(int position) {
        return this.data.get(position);
    }
    private View getItemView(ViewGroup container) {
        return this.mLayoutInflater.inflate(layoutResId, container, false);
    }

    protected Context getContext() {
        if (mContext == null) {
            return AppUtils.getContext();
        }
        return mContext;
    }

    protected abstract void convert(ItemViewHelper helper, T data);

}
