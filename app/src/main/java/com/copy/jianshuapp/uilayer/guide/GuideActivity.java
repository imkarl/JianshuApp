package com.copy.jianshuapp.uilayer.guide;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.copy.jianshuapp.R;
import com.copy.jianshuapp.common.AppUtils;
import com.copy.jianshuapp.uilayer.base.BaseActivity;
import com.copy.jianshuapp.uilayer.home.activitys.MainActivity;
import com.copy.jianshuapp.uilayer.login.activity.LoginActivity;
import com.copy.jianshuapp.uilayer.widget.FixViewPager;
import com.copy.jianshuapp.uilayer.widget.adapter.BasePagerAdapter;
import com.viewpagerindicator.CirclePageIndicator;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 引导页
 * @version imkarl 2017-03
 */
public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {

    @Bind(R.id.viewPager)
    FixViewPager mViewPager;
    @Bind(R.id.indicator)
    CirclePageIndicator mIndicator;
    @Bind(R.id.tv_skip)
    TextView mTvSkip;
    @Bind(R.id.tv_enter)
    TextView mTvEnter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        mViewPager.setAdapter(new BasePagerAdapter(getContext(),
                R.layout.layout_guide_0,
                R.layout.layout_guide_1,
                R.layout.layout_guide_2,
                R.layout.layout_guide_3));

        mIndicator.setViewPager(mViewPager);

        mViewPager.addOnPageChangeListener(this);

        mTvSkip.setOnClickListener(view -> {
            finish();
        });
        mTvEnter.setOnClickListener(view -> {
            finish();
        });
    }

    @Override
    public void finish() {
        // 判断是否已登录
        if (AppUtils.isLogin()) {
            startActivity(MainActivity.class);
        } else {
            startActivity(LoginActivity.launchRegister());
        }

        super.finish();
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    @Override
    public void onPageSelected(int position) {
        if (position == mViewPager.getAdapter().getCount() - 1) {
            mTvSkip.setVisibility(View.GONE);
            mTvEnter.setVisibility(View.VISIBLE);
        } else {
            mTvSkip.setVisibility(View.VISIBLE);
            mTvEnter.setVisibility(View.GONE);
        }
    }
    @Override
    public void onPageScrollStateChanged(int state) {
    }

}
