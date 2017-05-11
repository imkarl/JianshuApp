package com.copy.jianshuapp.uilayer.home.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.copy.jianshuapp.R;
import com.copy.jianshuapp.common.DisplayInfo;
import com.copy.jianshuapp.uilayer.base.BaseFragment;
import com.copy.jianshuapp.uilayer.home.adapters.NotificationAdapter;
import com.copy.jianshuapp.uilayer.search.SearchActivity;
import com.copy.jianshuapp.uilayer.settings.SettingNotificationActivity;
import com.copy.jianshuapp.uilayer.widget.JSSwipeRefreshLayout;
import com.jakewharton.rxbinding2.view.RxView;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 消息
 * @version imkarl 2017-04
 */
public class NotificationFragment extends BaseFragment {

    @Bind(R.id.view_status_height)
    View mViewStatusHeight;
    @Bind(R.id.text_title_user)
    TextView mTextTitleUser;
    @Bind(R.id.iv_notification_setting)
    ImageView mIvNotificationSetting;
    @Bind(R.id.iv_search)
    ImageView mIvSearch;
    @Bind(R.id.frame_toolbar)
    RelativeLayout mFrameToolbar;
    @Bind(R.id.topLine)
    View mTopLine;
    @Bind(R.id.recycler)
    RecyclerView mRecycler;
    @Bind(R.id.refresh_view)
    JSSwipeRefreshLayout mRefreshView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_notification);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mViewStatusHeight.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayInfo.getStatusBarHeight()));
        }

        initView();
    }

    private void initView() {
        mRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecycler.setAdapter(new NotificationAdapter());

        RxView.clicks(mIvNotificationSetting)
                .throttleFirst(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(it -> {
                    SettingNotificationActivity.launch();
                });
        RxView.clicks(mIvSearch)
                .throttleFirst(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(it -> {
                    SearchActivity.launch();
                });
    }

}
