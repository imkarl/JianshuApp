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
import com.copy.jianshuapp.common.LogUtils;
import com.copy.jianshuapp.modellayer.model.Account;
import com.copy.jianshuapp.modellayer.repository.SubscriptionRepository;
import com.copy.jianshuapp.uilayer.base.BaseActivity;
import com.copy.jianshuapp.uilayer.home.fragments.DiscoverFragment;
import com.copy.jianshuapp.uilayer.home.fragments.DynamicFragment;
import com.copy.jianshuapp.uilayer.home.fragments.MainPageFragment;
import com.copy.jianshuapp.uilayer.home.fragments.MoreFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 主界面
 * @version imkarl 2017-04
 */
public class MainActivity extends BaseActivity implements View.OnClickListener {

    public static Intent launch() {
        Intent intent = new Intent(AppUtils.getContext(), MainActivity.class);
        return intent;
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

    private MainPageFragment fragmentMainPage;
    private DiscoverFragment fragmentDiscover;
    private DynamicFragment fragmentDynamic;
    private MoreFragment fragmentMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        hideAllFragment();

        // 显示
        showNavigations(AppUtils.getLoginAccount());

        SubscriptionRepository.list().subscribe(it -> {
            LogUtils.e(it);
        }, err -> {
            LogUtils.e(err);
        });
        LogUtils.e("account=" + AppUtils.getLoginAccount());

    }

    public void showNavigations(Account account) {
        if (account == null) {
            this.mNavigationBarMain.setVisibility(View.GONE);
            this.mNavigationBarSign.setVisibility(View.VISIBLE);

            this.mNavigationBarSign.findViewById(R.id.tab_login).setOnClickListener(this);
            this.mNavigationBarSign.findViewById(R.id.tab_register).setOnClickListener(this);

            showFragment(fragmentDiscover, DiscoverFragment.class);
        } else {
            this.mNavigationBarMain.setVisibility(View.VISIBLE);
            this.mNavigationBarSign.setVisibility(View.GONE);

            this.mNavigationBarMain.findViewById(R.id.tab_main_page).setOnClickListener(this);
            this.mNavigationBarMain.findViewById(R.id.tab_discover).setOnClickListener(this);
            this.mNavigationBarMain.findViewById(R.id.tab_writting).setOnClickListener(this);
            this.mNavigationBarMain.findViewById(R.id.tab_dynamic).setOnClickListener(this);
            this.mNavigationBarMain.findViewById(R.id.tab_more).setOnClickListener(this);

            switchTab(this.mNavigationBarMain.findViewById(R.id.tab_main_page));
        }
    }

    @Override
    public void onClick(View view) {
        switchTab(view);
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

                showFragment(fragmentDiscover, DiscoverFragment.class);
                break;
            case R.id.tab_main_page:
                setSelected(view);
                mCurrentTab = id;

                showFragment(fragmentMainPage, MainPageFragment.class);
                break;
            case R.id.tab_writting:
                setSelected(view);
//                gotoEditor();
                final View lastView = findViewById(mCurrentTab);
                mCurrentTab = id;
                switchTab(lastView);
                break;
            case R.id.tab_dynamic:
                setSelected(view);
                mCurrentTab = id;

                showFragment(fragmentDynamic, DynamicFragment.class);
                break;
            case R.id.tab_more:
                setSelected(view);
                mCurrentTab = id;

                showFragment(fragmentMore, MoreFragment.class);
                break;
        }
    }

    private <T extends Fragment> void showFragment(T fragment, Class<T> fragmentClass) {
        if (fragment == null) {
            fragment = getFragmentHelper().findOrCreate(fragmentClass);
        }
        getFragmentHelper().beginTransaction()
                .add(R.id.container, fragment)
                .show(fragment)
                .commit();
    }

    private void hideAllFragment() {
        getFragmentHelper()
                .beginTransaction()
                .hide(fragmentMainPage)
                .hide(fragmentDiscover)
                .hide(fragmentDynamic)
                .hide(fragmentMore)
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
