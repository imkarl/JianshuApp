package com.copy.jianshuapp.uilayer.plugin;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.copy.jianshuapp.BuildConfig;
import com.copy.jianshuapp.R;
import com.copy.jianshuapp.common.AppUtils;
import com.copy.jianshuapp.exception.IllegalException;
import com.copy.jianshuapp.uilayer.widget.webview.SafeWebChromeClient;
import com.copy.jianshuapp.uilayer.widget.webview.SafeWebView;
import com.copy.jianshuapp.uilayer.widget.webview.SafeWebViewClient;
import com.github.ybq.android.spinkit.style.FadingCircle;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 插件
 * @version imkarl 2017-03
 */
public abstract class Plugin {
    private static final int ID_PLUGIN = R.id.global_view_id;

    private static final int PROGRESS_INJECT = 90;

    protected static <T extends Plugin> T inject(Activity activity, Class<T> pluginClass) {
        ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
        T plugin = (T) contentView.getTag(ID_PLUGIN);
        if (plugin == null) {
            try {
                plugin = pluginClass.newInstance();
            } catch (Exception e) {
                throw new IllegalException("没有无参构造方法");
            }
            plugin.init(contentView);
            contentView.setTag(ID_PLUGIN, plugin);
        }
        return plugin;
    }

    private ViewGroup mRootLayout;
    private SafeWebView mWebView;
    private ProgressBar mLoadingView;
    private boolean mPageFinish;
    private boolean mIsInit;
    private Set<OnLoadFinish> mOnLoadFinishs = new HashSet<>();

    /** 是否可见 */
    private boolean visible = false;

    private final boolean isDebug = BuildConfig.DEBUG;

    protected void init(ViewGroup parent) {
        init(parent, -1, -1);
    }
    protected void init(ViewGroup parent, int width, int height) {
        if (!isDebug && (width <= 0 || height <= 0)) {
            width = height = 1;
        }
        visible = (width != 1 && height != 1);

        // 用于显示遮罩
        mRootLayout = new FrameLayout(parent.getContext());
        mRootLayout.setClickable(true);
        parent.addView(mRootLayout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        // 用于加载网页
        mWebView = new SafeWebView(parent.getContext());
        mRootLayout.addView(mWebView, new FrameLayout.LayoutParams(width, height, Gravity.CENTER));

        // 初始化
        onInit();
    }

    protected String getInjectedName() {
        return null;
    }
    protected Class getInjectedClass() {
        return null;
    }

    protected void onInit() {
        if (visible) {
            mWebView.setFocusable(true);
            mWebView.requestFocus();
            mWebView.setOnKeyListener((v, keyCode, event) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    if (event.getAction() == KeyEvent.ACTION_UP) {
                        onBackPressed();
                    }
                    return true;
                }
                return false;
            });
        }

        mWebView.setWebViewClient(new SafeWebViewClient());
        mWebView.injectJsCallJava(new SafeWebChromeClient(getInjectedName(), getInjectedClass()) {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                if (newProgress <= PROGRESS_INJECT) {
                    mPageFinish = false;
                } else {
                    if (!mIsInit) {
                        onLoadFinish();
                        mIsInit = true;

                        nofityOnLoadFinish();
                    }
                    mPageFinish = true;
                }
            }
        });

        mWebView.loadUrl(getCurrentLink());
    }

    protected void setSize(int width, int height) {
        if (mWebView != null) {
            mWebView.setLayoutParams(new ViewGroup.LayoutParams(width, height));
        }
    }

    /**
     * 设置是否显示覆盖效果（背景半透明遮罩）
     */
    public void setOverlay(boolean enable) {
        if (enable) {
            mRootLayout.setBackgroundResource(R.color.half_transaction);
        } else {
            mRootLayout.setBackgroundResource(R.color.transparent);
        }
    }

    /**
     * 给HTML网页添加灰色覆盖层
     */
    public void addHtmlOverlay() {
        runJS("$('body').append('<div id=\"overlay\"></div>');"
                +"$('#overlay').height($(document).height()).css({'opacity':1, 'position':'absolute', 'top':0, 'left':0, 'background-color':'#DDDDDD', 'width':'100%', 'z-index':2000});");
    }


    /**
     * 设置加载进度条
     */
    public void setLoading(boolean enable) {
        if (mRootLayout == null) {
            return;
        }

        Observable.just(enable)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(it -> {
                    if (mRootLayout == null) {
                        return;
                    }

                    if (enable) {
                        if (mLoadingView == null) {
                            Context context = mRootLayout.getContext();
                            mLoadingView = new ProgressBar(context);
                            FadingCircle drawable = new FadingCircle();
                            drawable.setColor(context.getResources().getColor(R.color.blue300));
                            mLoadingView.setIndeterminateDrawable(drawable);
                            mLoadingView.setBackgroundResource(R.color.white);
                        }
                        if (mLoadingView.getParent() == null) {
                            int width = mWebView.getWidth();
                            int height = mWebView.getHeight();
                            int paddingHorizontal = (width - AppUtils.dp2px(40)) / 2;
                            int paddingVertical = (height - AppUtils.dp2px(40)) / 2;
                            mRootLayout.addView(mLoadingView, new FrameLayout.LayoutParams(width, height, Gravity.CENTER));
                            mLoadingView.setPadding(paddingHorizontal, paddingVertical, paddingHorizontal, paddingVertical);
                        }
                    } else {
                        if (mLoadingView != null) {
                            mRootLayout.removeView(mLoadingView);
                        }
                    }
                });
    }

    protected abstract String getCurrentLink();

    protected void onLoadFinish() {
    }

    public void onBackPressed() {
        destroy();
    }

    public void destroy() {
        this.mOnLoadFinishs.clear();

        if (mWebView != null) {
            ViewGroup contentView = (ViewGroup) mWebView.getRootView().findViewById(android.R.id.content);
            contentView.setTag(ID_PLUGIN, null);
            contentView.removeView(mRootLayout);
            mRootLayout.removeView(mWebView);
            mWebView.destroy();
            mRootLayout = null;
            mWebView = null;
        }
    }

    public <T extends Plugin> T addOnLoadFinish(OnLoadFinish listener) {
        this.mOnLoadFinishs.add(listener);

        if (mPageFinish) {
            nofityOnLoadFinish();
        }

        return (T) this;
    }

    private void nofityOnLoadFinish() {
        if (mOnLoadFinishs != null) {
            // 延迟一段时间，确保html渲染完成
            Observable.fromIterable(mOnLoadFinishs)
                    .delay(300, TimeUnit.MILLISECONDS)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(listener -> {
                        if (listener != null) {
                            listener.onLoadFinish();
                            mOnLoadFinishs.remove(listener);
                        }
                    });
        }
    }

    public Plugin runJS(String js) {
        Observable.just(js)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(it -> {
                    String runJs = "javascript:"+js;
                    mWebView.loadUrl(runJs);
                });
        return this;
    }

    protected SafeWebView getWebView() {
        return mWebView;
    }
    public boolean isPageFinish() {
        return mPageFinish;
    }

}
