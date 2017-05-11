package com.copy.jianshuapp.uilayer.home.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.copy.jianshuapp.R;
import com.copy.jianshuapp.common.DisplayInfo;
import com.copy.jianshuapp.uilayer.base.BaseFragment;

import butterknife.Bind;

/**
 * 我的
 * @version imkarl 2017-04
 */
public class MeFragment extends BaseFragment {

    @Bind(R.id.linear_root)
    ViewGroup mLinearRoot;
    @Bind(R.id.rl_title_bar)
    RelativeLayout mRlTitleBar;
    @Bind(R.id.scroll_user_content)
    ScrollView mScrollUserContent;
    @Bind(R.id.text_user_name)
    TextView mTextUserName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById(R.id.view_status_height).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayInfo.getStatusBarHeight()));
        }
    }

}
