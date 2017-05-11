package com.copy.jianshuapp.uilayer.widget.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.copy.jianshuapp.common.AppUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * PagerAdapter基类
 * @version imkarl 2017-03
 */
public class BasePagerAdapter extends PagerAdapter {

    private final List<View> views;

    public BasePagerAdapter(List<View> views) {
        this.views = views;
    }
    public BasePagerAdapter(View... views) {
        this.views = Arrays.asList(views);
    }

    public BasePagerAdapter(Context context, @LayoutRes Integer... layoutIds) {
        this(context, Arrays.asList(layoutIds));
    }
    public BasePagerAdapter(Context context, List<Integer> layoutIds) {
        this.views = new ArrayList<>();
        LayoutInflater inflater = LayoutInflater.from(context);
        for (int layoutId : layoutIds) {
            this.views.add(inflater.inflate(layoutId, null));
        }
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = views.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
