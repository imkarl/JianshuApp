package com.copy.jianshuapp.uilayer.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.copy.jianshuapp.R;
import com.copy.jianshuapp.common.AppUtils;
import com.copy.jianshuapp.common.LogUtils;
import com.copy.jianshuapp.common.ObjectUtils;
import com.copy.jianshuapp.uilayer.listeners.OnLoadmoreListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * RecyclerAdapter基类
 * @version imkarl 2016-08
 */
public abstract class BaseRecyclerAdapter<T> extends BaseQuickAdapter<T> {

    private LoadmoreWrapper mWrapper;
    private boolean mEnableLoadmore = false;
    private OnLoadmoreListener mLoadmoreListener;
    private View mAvoidErrorView;

    @Deprecated
    public BaseRecyclerAdapter(List<T> data, @LayoutRes int layoutResId) {
        super(layoutResId, handle(data));
    }
    public BaseRecyclerAdapter(@LayoutRes int layoutResId) {
        super(layoutResId, handle(null));
    }
    public BaseRecyclerAdapter(@LayoutRes int layoutResId, Collection<T> data) {
        super(layoutResId, handle(data));
    }

    private static <T> List<T> handle(Collection<T> data) {
        if (ObjectUtils.isEmpty(data)) {
            return new ArrayList<>();
        } else {
            return new ArrayList<>(data);
        }
    }

    public void setData(Collection<T> data) {
        List<T> oldData = getData();
        oldData.clear();
        oldData.addAll(data);
    }

    protected Context getContext() {
        if (mContext == null) {
            return AppUtils.getContext();
        }
        return mContext;
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int positions) {
        int viewType = holder.getItemViewType();
        switch(viewType) {
            case 0:
                this.convert((BaseViewHolder)holder, this.getItem(holder.getLayoutPosition() - this.getHeaderLayoutCount()));
                return;

            case HEADER_VIEW:
            case FOOTER_VIEW:
            case EMPTY_VIEW:
            case LOADING_VIEW:
                break;

            default:
                T entity = this.getItem(holder.getLayoutPosition() - this.getHeaderLayoutCount());
                this.convert((BaseViewHolder)holder, entity);
                this.onBindDefViewHolder((BaseViewHolder)holder, entity);
                return;
        }

        super.onBindViewHolder(holder, positions);
    }
    protected abstract void convert(BaseViewHolder holder, T entity);

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == FOOTER_VIEW || viewType == 0) {
            try {
                return super.onCreateViewHolder(parent, viewType);
            } catch (IllegalArgumentException e) {
                if (mAvoidErrorView == null) {
                    mAvoidErrorView = new View(getContext());
                }
                return new BaseViewHolder(this.mAvoidErrorView) { };
            }
        }
        return super.onCreateViewHolder(parent, viewType);
    }

    public void setHeaderView(View header) {
        if (header != null) {
            if (header.getParent() == null) {
                addHeaderView(header);
            }
        } else {
            removeAllHeaderView();
        }
    }

    @Deprecated
    @Override
    public void openLoadMore(int pageSize) {
        //super.openLoadMore(pageSize);
        enableLoadmore(true);
    }

    /**
     * 是否启用加载更多
     * @param enable true:启用，false:关闭
     */
    public void enableLoadmore(boolean enable) {
        if (mEnableLoadmore == enable) {
            return;
        }

        mEnableLoadmore = enable;
        if (mWrapper != null) {
            mWrapper.setLoadMoreEnabled(mEnableLoadmore);
        }
    }

    /**
     * 是否已启用加载更多
     */
    public boolean isEnableLoadmore() {
        return mEnableLoadmore;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        // 支持加载更多
        mWrapper = LoadmoreWrapper.with(this);
        mWrapper.setFooterView(R.layout.footer_item_load_more)
                .setLoadMoreEnabled(mEnableLoadmore)
                .setListener(enabled -> {
                    if (!mEnableLoadmore || getData().isEmpty()) {
                        return;
                    }
                    if (mLoadmoreListener != null) {
                        mLoadmoreListener.onLoadmore();
                    }
                })
                .into(recyclerView);
    }

    /**
     * 加载更多完成
     */
    public void loadmoreComplete() {
        try {
            super.loadComplete();
        } catch (Throwable e) {
            LogUtils.e(e);
        }
        notifyDataSetChanged();
    }

    @Deprecated
    @Override
    public void loadComplete() {
        loadmoreComplete();
    }

    @Deprecated
    @Override
    public void showLoadMoreFailedView() {
        loadmoreComplete();
    }

    @Deprecated
    @Override
    public void setOnLoadMoreListener(RequestLoadMoreListener requestLoadMoreListener) {
        super.setOnLoadMoreListener(requestLoadMoreListener);
    }

    /**
     * 设置加载更多监听
     */
    public void setOnLoadmoreListener(OnLoadmoreListener listener) {
        this.mLoadmoreListener = listener;
    }

    /**
     * FIXME 剔除缓存的数据
     * @tips 避免预加载缓存导致的数据重复
     */
    public void removeCache(OnClearListener<T> listener) {
        boolean flag = false;
        Iterator<T> iterator = getData().iterator();
        while (iterator.hasNext()) {
            T item = iterator.next();
            if (flag) {
                iterator.remove();
            }
            if (listener.onShouldClear(item)) {
                flag = true;
            }
        }
    }

    public interface OnClearListener<T> {
        /**
         * 是否应该开始剔除缓存
         * @return true：应该开始剔除
         */
        boolean onShouldClear(T item);
    }
}
