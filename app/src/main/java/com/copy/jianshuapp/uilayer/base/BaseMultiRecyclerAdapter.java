package com.copy.jianshuapp.uilayer.base;

import android.support.annotation.LayoutRes;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

/**
 * 多类型RecyclerAdapter基类
 * @version imkarl 2016-08
 */
public abstract class BaseMultiRecyclerAdapter<T> extends BaseRecyclerAdapter<T> {

    public BaseMultiRecyclerAdapter() {
        this(null);
    }
    public BaseMultiRecyclerAdapter(List<T> data) {
        super(0, data);
    }

    @Deprecated
    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Deprecated
    @Override
    protected int getDefItemViewType(int position) {
        T data = this.getItem(position);
        return getItemLayoutId(position, data);
    }


    /**
     * 获取Item对应的Layout
     */
    protected abstract @LayoutRes int getItemLayoutId(int position, T data);

    @Override
    protected BaseViewHolder onCreateDefViewHolder(ViewGroup parent, int viewType) {
        return createBaseViewHolder(parent, viewType);
    }

    protected final void convert(BaseViewHolder holder, T entity) {
        this.convert(holder, entity, getItemViewType(holder.getLayoutPosition()));
    }

    protected abstract void convert(BaseViewHolder holder, T entity, @LayoutRes int layoutId);

}
