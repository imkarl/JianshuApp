package com.copy.jianshuapp.uilayer.login.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.copy.jianshuapp.R;
import com.copy.jianshuapp.uilayer.base.BaseFragment;

/**
 * 用户注册
 * @version imkarl 2017-03
 */
public class RegisterFragment extends BaseFragment {

    private View mRootView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        this.mRootView = inflater.inflate(R.layout.fragment_register, container, false);
        return this.mRootView;
    }

}
