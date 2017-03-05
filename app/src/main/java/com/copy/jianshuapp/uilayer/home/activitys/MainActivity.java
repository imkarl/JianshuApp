package com.copy.jianshuapp.uilayer.home.activitys;

import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.copy.jianshuapp.R;
import com.copy.jianshuapp.uilayer.BaseActivity;
import com.copy.jianshuapp.uilayer.home.fragments.MainDiscoverFragment;
import com.copy.jianshuapp.uilayer.home.fragments.MainDynamicFragment;
import com.copy.jianshuapp.uilayer.home.fragments.MainHomeFragment;
import com.copy.jianshuapp.uilayer.home.fragments.MainMoreFragment;
import com.copy.jianshuapp.uilayer.home.views.listeners.OnMultiClickListener;
import com.copy.jianshuapp.utils.JSToast;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

//    @Bind(R.id.tab_main_page)
//    FrameLayout tabMainPage;
//    @Bind(R.id.tab_discover)
//    RelativeLayout tabDiscover;
//    @Bind(R.id.tab_writting)
//    FrameLayout tabWritting;
//    @Bind(R.id.tab_dynamic)
//    RelativeLayout tabDynamic;
//    @Bind(R.id.tab_more)
//    FrameLayout tabMore;
//
//    @Bind(R.id.tv_home)
//    TextView tvHome;
//    @Bind(R.id.tv_discover)
//    TextView tvDiscover;
//    @Bind(R.id.tv_dynamic)
//    TextView tvDynamic;
//    @Bind(R.id.tv_more)
//    TextView tvMore;
//
//    @Bind(R.id.navigation_bar)
//    LinearLayout navigationBar;
//
//    private int mCurrentTab = 0;
//
//    private MainHomeFragment fragmentHome;
//    private MainDiscoverFragment fragmentDiscover;
//    private MainDynamicFragment fragmentDynamic;
//    private MainMoreFragment fragmentMore;
//
//    private OnMultiClickListener homeClickListener = new OnMultiClickListener() {
//        @Override
//        public void onGlobalClick(View view) {
//            switchTab(view);
//        }
//        @Override
//        public void onDoubleClick(View view) {
//            if (view == tabMainPage && mCurrentTab == 0) {
//                fragmentHome.doubleClick();
//            }
//        }
//    };
//
//
//    private View.OnClickListener tabClickListener = new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
//            switchTab(view);
//        }
//    };
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_main);
//        ButterKnife.bind(this);
//
//        setViewListener();
//
//    }
//
//
//    protected void setViewListener() {
//        tabMainPage.setOnClickListener(this.homeClickListener);
//        tabDiscover.setOnClickListener(this.tabClickListener);
//        tabDynamic.setOnClickListener(this.tabClickListener);
//        tabMore.setOnClickListener(this.tabClickListener);
//        tabWritting.setOnClickListener(this.tabClickListener);
//        navigationBar.getChildAt(this.mCurrentTab).performClick();
//    }
//
//
//    protected void switchTab(View view) {
//        int viewId = view.getId();
//        if (viewId == R.id.tab_writting) {
//            JSToast.show(getContext(), "writting");
//            return;
//        }
//
//        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
//        Bundle bundle = new Bundle();
//
//        hideFragment(fragmentHome, transaction);
//        hideFragment(fragmentDiscover, transaction);
//        hideFragment(fragmentDynamic, transaction);
//        hideFragment(fragmentMore, transaction);
//
//        resetCompoundDrawables(tvHome);
//        resetCompoundDrawables(tvDiscover);
//        resetCompoundDrawables(tvDynamic);
//        resetCompoundDrawables(tvMore);
//
//        switch (viewId) {
//            case R.id.tab_main_page:
//                fragmentHome = showFragment(MainHomeFragment.class, fragmentHome, bundle, transaction);
//                setCompoundDrawablesColorResFilter(tvHome, R.color.red300);
//                break;
//
//            case R.id.tab_discover:
//                fragmentDiscover = showFragment(MainDiscoverFragment.class, fragmentDiscover, bundle, transaction);
//                setCompoundDrawablesColorResFilter(tvDiscover, R.color.red300);
//                break;
//
//            case R.id.tab_dynamic:
//                fragmentDynamic = showFragment(MainDynamicFragment.class, fragmentDynamic, bundle, transaction);
//                setCompoundDrawablesColorResFilter(tvDynamic, R.color.red300);
//                break;
//
//            case R.id.tab_more:
//                fragmentMore = showFragment(MainMoreFragment.class, fragmentMore, bundle, transaction);
//                setCompoundDrawablesColorResFilter(tvMore, R.color.red300);
//                break;
//        }
//
//        transaction.commit();
//    }
//
//
//
//
//    private <T extends Fragment> T showFragment(Class<T> fragmentClass, T fragment, Bundle bundle, FragmentTransaction transaction) {
//        if (fragment == null) {
//            fragment = findSupportFragment(fragmentClass);
//        }
//        if (fragment == null || fragment.isDetached()) {
//            fragment = newSupportFragment(fragmentClass, bundle);
//            transaction.add(R.id.fragment_container, fragment, fragment.getClass().getName());
//        }
//        transaction.show(fragment);
//        return fragment;
//    }
//    private void hideFragment(Fragment fragment, FragmentTransaction transaction) {
//        if (fragment != null && !fragment.isDetached()) {
//            transaction.hide(fragment);
//        }
//    }
//    private void removeFragment(Fragment fragment, FragmentTransaction transaction) {
//        if (fragment != null && !fragment.isDetached()) {
//            transaction.remove(fragment);
//        }
//    }
//
//    private void setCompoundDrawablesColorResFilter(TextView view, int color) {
//        setCompoundDrawablesColorFilter(view, getResources().getColor(color));
//    }
//    private void setCompoundDrawablesColorFilter(TextView view, int color) {
//        view.setTextColor(color);
//
//        ColorFilter filter = new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP);
//        Drawable[] drawables = view.getCompoundDrawables();
//        for (Drawable drawable : drawables) {
//            if (drawable != null) {
//                drawable.setColorFilter(filter);
//            }
//        }
//        view.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
//    }
//    private void resetCompoundDrawables(TextView view) {
//        view.setTextColor(getResources().getColor(R.color.selector_color_theme_88));
//
//        Drawable[] drawables = view.getCompoundDrawables();
//        for (Drawable drawable : drawables) {
//            if (drawable != null) {
//                drawable.setColorFilter(null);
//            }
//        }
//        view.setCompoundDrawables(drawables[0], drawables[1], drawables[2], drawables[3]);
//    }

}
