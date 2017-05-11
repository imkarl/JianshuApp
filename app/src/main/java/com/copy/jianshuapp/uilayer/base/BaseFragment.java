package com.copy.jianshuapp.uilayer.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.copy.jianshuapp.exception.IllegalException;
import com.trello.rxlifecycle2.components.RxFragment;

import butterknife.ButterKnife;

/**
 * Fragment基类
 * @author imkarl 2017-03
 * @author imkarl 2017-03 新增setContentView
 */
public class BaseFragment extends RxFragment {

    @LayoutRes
    private int mContentViewId;
    private View mContentView;

    private View mRootView;

    private FragmentManagerHelper mFragmentManagerHelper = new FragmentManagerHelper(this);

    @Deprecated
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public Context getContext() {
        return this.getActivity();
    }

    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        if (this.mContentView != null) {
            view = this.mContentView;
        } else if (this.mContentViewId > 0) {
            view = inflater.inflate(this.mContentViewId, container, false);
        }
        if (view == null) {
            throw new IllegalException("Must set 'contentView'.");
        }

        this.mContentView = null;
        this.mContentViewId = -1;
        mRootView = view;

        ButterKnife.bind(this, view);
        return view;
    }
    public void setContentView(@LayoutRes int layoutResId) {
        this.mContentViewId = layoutResId;
        this.mContentView = null;
    }
    public void setContentView(View view) {
        this.mContentViewId = -1;
        this.mContentView = view;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    public View findViewById(@IdRes int viewId) {
        return mRootView.findViewById(viewId);
    }


    public FragmentManagerHelper getFragmentHelper() {
        return mFragmentManagerHelper;
    }
    public FragmentTransactionHelper beginTransaction() {
        return new FragmentTransactionHelper(mFragmentManagerHelper);
    }

}
