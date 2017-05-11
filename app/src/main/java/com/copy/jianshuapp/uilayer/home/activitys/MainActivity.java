package com.copy.jianshuapp.uilayer.home.activitys;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.copy.jianshuapp.R;
import com.copy.jianshuapp.common.AppUtils;
import com.copy.jianshuapp.uilayer.base.BaseActivity;
import com.copy.jianshuapp.uilayer.editor.EditorActivity;
import com.copy.jianshuapp.uilayer.home.fragments.MeFragment;
import com.copy.jianshuapp.uilayer.home.fragments.NotificationFragment;
import com.copy.jianshuapp.uilayer.home.fragments.SubscriptionFragment;
import com.copy.jianshuapp.uilayer.home.fragments.TrendFragment;
import com.copy.jianshuapp.uilayer.login.activity.LoginActivity;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 主界面
 * @version imkarl 2017-04
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    public static void launch() {
        startActivity(MainActivity.class);
    }

    @Bind(R.id.container)
    FrameLayout mContainer;
    @Bind(R.id.separate_line_main)
    View mSeparateLineMain;
    @Bind(R.id.ll_follow)
    LinearLayout mLlFollow;
    @Bind(R.id.img_guanzhu_unread)
    ImageView mImgGuanzhuUnread;
    @Bind(R.id.iv_home)
    ImageView mIvHome;
    @Bind(R.id.tv_discover)
    TextView mTvDiscover;
    @Bind(R.id.iv_write)
    ImageView mIvWrite;
    @Bind(R.id.tv_writting_bg)
    TextView mTvWrittingBg;
    @Bind(R.id.iv_notification)
    ImageView mIvNotification;
    @Bind(R.id.tv_msg)
    TextView mTvMsg;
    @Bind(R.id.ll_notification)
    LinearLayout mLlNotification;
    @Bind(R.id.text_notify_sum_count)
    TextView mTextNotifySumCount;
    @Bind(R.id.iv_mine)
    ImageView mIvMine;
    @Bind(R.id.tv_more_menu)
    TextView mTvMoreMenu;
    @Bind(R.id.menu_anchor)
    View mMenuAnchor;
    @Bind(R.id.navigation_bar_main)
    LinearLayout mNavigationBarMain;
    @Bind(R.id.text_login)
    TextView mTextLogin;
    @Bind(R.id.divider_line)
    View mDividerLine;
    @Bind(R.id.text_register)
    TextView mTextRegister;
    @Bind(R.id.navigation_bar_sign)
    LinearLayout mNavigationBarSign;

    private int mCurrentTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        hideAllFragment();

        // 显示导航栏
        showNavigations();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        // 显示导航栏
        showNavigations();
    }

    @Override
    public boolean isSupportSwipeBack() {
        return false;
    }

    public void showNavigations() {
        if (AppUtils.isLogin()) {
            this.mNavigationBarMain.setVisibility(View.VISIBLE);
            this.mNavigationBarSign.setVisibility(View.GONE);

            this.mNavigationBarMain.findViewById(R.id.tab_main_page).setOnClickListener(this);
            this.mNavigationBarMain.findViewById(R.id.tab_discover).setOnClickListener(this);
            this.mNavigationBarMain.findViewById(R.id.tab_writting).setOnClickListener(this);
            this.mNavigationBarMain.findViewById(R.id.tab_notification).setOnClickListener(this);
            this.mNavigationBarMain.findViewById(R.id.tab_more).setOnClickListener(this);

            switchTab(this.mNavigationBarMain.findViewById(R.id.tab_main_page));
        } else {
            this.mNavigationBarMain.setVisibility(View.GONE);
            this.mNavigationBarSign.setVisibility(View.VISIBLE);

            this.mNavigationBarSign.findViewById(R.id.tab_login).setOnClickListener(this);
            this.mNavigationBarSign.findViewById(R.id.tab_register).setOnClickListener(this);

            showFragment(TrendFragment.class);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tab_login:
                LoginActivity.launchLogin();
                break;
            case R.id.tab_register:
                LoginActivity.launchRegister();
                break;

            default:
                switchTab(view);
                break;
        }
    }

    public void switchTab(View view) {
        final int id = view.getId();
        if (mCurrentTab == id) {
            return;
        }

        hideAllFragment();

        switch (id) {
            case R.id.tab_discover:
                setSelected(view);
                mCurrentTab = id;

                showFragment(TrendFragment.class);
                break;
            case R.id.tab_main_page:
                setSelected(view);
                mCurrentTab = id;

                showFragment(SubscriptionFragment.class);
                break;
            case R.id.tab_writting:
                setSelected(view);
                EditorActivity.launch();
                final View lastView = findViewById(mCurrentTab);
                mCurrentTab = id;
                switchTab(lastView);
                break;
            case R.id.tab_notification:
                setSelected(view);
                mCurrentTab = id;

                showFragment(NotificationFragment.class);
                break;
            case R.id.tab_more:
                setSelected(view);
                mCurrentTab = id;

                showFragment(MeFragment.class);
                break;
        }
    }

    private <T extends Fragment> void showFragment(Class<T> fragmentClass) {
        getFragmentHelper().beginTransaction()
                .add(R.id.container, fragmentClass)
                .show(fragmentClass)
                .commit();
    }

    private void hideAllFragment() {
        getFragmentHelper()
                .beginTransaction()
                .hide(SubscriptionFragment.class)
                .hide(TrendFragment.class)
                .hide(NotificationFragment.class)
                .hide(MeFragment.class)
                .commit();
    }

    private static boolean setSelected(View view) {
        boolean ret = false;
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent == null) {
            return false;
        }
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);
            if (view == child) {
                child.setSelected(true);
                ret = true;
            } else {
                child.setSelected(false);
            }
        }
        return ret;
    }

}
