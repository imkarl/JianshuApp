package com.copy.jianshuapp.uilayer.listeners;

/**
 * 加载完成监听器
 * @version imkarl 2017-04
 */
public interface OnLoadedListener<T> {
    void onSuccess(T data);
    void onFail();
}
