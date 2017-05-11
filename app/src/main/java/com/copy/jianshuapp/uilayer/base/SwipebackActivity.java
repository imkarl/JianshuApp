package com.copy.jianshuapp.uilayer.base;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.copy.jianshuapp.R;
import com.copy.jianshuapp.common.ActivityLifcycleManager;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

/**
 * 支持滑动返回的Activity
 * @author imkarl 2017-05
 */
class SwipebackActivity extends RxLifecycleActivity {

    private BGASwipeBackHelper mSwipeBackHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (isSupportSwipeBack()) {
            initSwipeback();
        }
        super.onCreate(savedInstanceState);
    }

    private void initSwipeback() {
        mSwipeBackHelper = new BGASwipeBackHelper(this, new BGASwipeBackHelper.Delegate() {
            @Override
            public boolean isSupportSwipeBack() {
                boolean isSupport = SwipebackActivity.this.isSupportSwipeBack();
                if (isSupport) {
                    int color = Color.argb((int) (255 * 0.8), 30, 30, 30);
                    getWindow().getDecorView().setBackgroundColor(color);
                }
                return isSupport;
            }
            @Override
            public void onSwipeBackLayoutSlide(float slideOffset) {
                int color = Color.argb((int) (255 * (1 - slideOffset) * 0.8), 30, 30, 30);
                getWindow().getDecorView().setBackgroundColor(color);
            }
            @Override
            public void onSwipeBackLayoutCancel() {
            }
            @Override
            public void onSwipeBackLayoutExecuted() {
                mSwipeBackHelper.swipeBackward();
            }
        });

        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true);
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true);
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(false);
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow);
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true);
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(false);
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.5F);
    }

    /**
     * 是否支持滑动返回
     */
    public boolean isSupportSwipeBack() {
        if (ActivityLifcycleManager.get().isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (mSwipeBackHelper == null) {
            super.onBackPressed();
            return;
        }

        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding()) {
            return;
        }
        mSwipeBackHelper.backward();
    }

}
