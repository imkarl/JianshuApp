package com.copy.jianshuapp.uilayer.base;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.github.nukc.LoadMoreWrapper.LoadMoreAdapter;

/**
 * 可以显示LoadmoreView的Adapter包装类
 * @tips 修正了LoadmoreView的显示状态
 * @version imkarl 2017-04
 */
class LoadmoreWrapper {
    private final LoadMoreAdapter mLoadMoreAdapter;

    public LoadmoreWrapper(LoadMoreAdapter loadMoreAdapter) {
        mLoadMoreAdapter = loadMoreAdapter;
    }

    public static LoadmoreWrapper with(RecyclerView.Adapter adapter) {
        LoadMoreAdapter loadMoreAdapter = new LoadMoreAdapter(adapter) {
            @Override
            public int getItemCount() {
                return getLoadMoreEnabled() ? super.getItemCount() : adapter.getItemCount();
            }
        };
        return new LoadmoreWrapper(loadMoreAdapter);
    }

    public LoadmoreWrapper setFooterView(@LayoutRes int resId) {
        mLoadMoreAdapter.setFooterView(resId);
        return this;
    }

    public LoadmoreWrapper setFooterView(View footerView) {
        mLoadMoreAdapter.setFooterView(footerView);
        return this;
    }

    public LoadmoreWrapper setNoMoreView(@LayoutRes int resId) {
        mLoadMoreAdapter.setNoMoreView(resId);
        return this;
    }

    public LoadmoreWrapper setNoMoreView(View noMoreView) {
        mLoadMoreAdapter.setNoMoreView(noMoreView);
        return this;
    }

    /**
     * 监听加载更多触发事件
     * @param listener {@link com.github.nukc.LoadMoreWrapper.LoadMoreAdapter.OnLoadMoreListener}
     */
    public LoadmoreWrapper setListener(LoadMoreAdapter.OnLoadMoreListener listener) {
        mLoadMoreAdapter.setLoadMoreListener(listener);
        return this;
    }

    /**
     * 设置是否启用加载更多
     * @param enabled default true
     */
    public LoadmoreWrapper setLoadMoreEnabled(boolean enabled) {
        mLoadMoreAdapter.setLoadMoreEnabled(enabled);
        if (!enabled) {
            mLoadMoreAdapter.setShouldRemove(true);
        }
        return this;
    }

    /**
     * 设置全部加载完后是否显示没有更多视图
     * @param enabled default false
     */
    public LoadmoreWrapper setShowNoMoreEnabled(boolean enabled) {
        mLoadMoreAdapter.setShowNoMoreEnabled(enabled);
        return this;
    }

    /**
     * 获取原来的 adapter
     */
    public RecyclerView.Adapter getOriginalAdapter() {
        return mLoadMoreAdapter.getOriginalAdapter();
    }

    public LoadMoreAdapter into(RecyclerView recyclerView) {
        recyclerView.setAdapter(mLoadMoreAdapter);
        return mLoadMoreAdapter;
    }
}
