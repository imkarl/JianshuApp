package com.copy.jianshuapp.uilayer.home.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.copy.jianshuapp.R;
import com.copy.jianshuapp.common.DisplayInfo;
import com.copy.jianshuapp.uilayer.base.BaseFragment;

import butterknife.Bind;

/**
 * 订阅\关注
 *
 * @version imkarl 2017-04
 */
public class SubscriptionFragment extends BaseFragment {

    @Bind(R.id.view_status_height)
    View mViewStatusHeight;
    @Bind(R.id.title)
    TextView mTitle;
    @Bind(R.id.iv_title)
    ImageView mIvTitle;
    @Bind(R.id.title_wrapper)
    LinearLayout mTitleWrapper;
    @Bind(R.id.add_subscribe)
    ImageView mAddSubscribe;
    @Bind(R.id.iv_search)
    ImageView mIvSearch;
    @Bind(R.id.divider)
    View mDivider;
    @Bind(R.id.subscribe_content)
    FrameLayout mSubscribeContent;
    @Bind(R.id.title_bar_root)
    LinearLayout mTitleBarRoot;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_subscribe_main);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            findViewById(R.id.view_status_height).setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayInfo.getStatusBarHeight()));
        }
    }
}
