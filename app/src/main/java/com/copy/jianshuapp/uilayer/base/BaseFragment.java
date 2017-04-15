package com.copy.jianshuapp.uilayer.base;

import android.app.Activity;
import android.content.Context;

import com.trello.rxlifecycle2.components.RxFragment;

/**
 * Fragment基类
 * @author imkarl 2017-03
 */
public class BaseFragment extends RxFragment {
    @Deprecated
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }
}
