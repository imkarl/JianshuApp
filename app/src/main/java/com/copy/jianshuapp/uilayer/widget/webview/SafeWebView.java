package com.copy.jianshuapp.uilayer.widget.webview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * 可提供Java与JavaScript安全互通的WebView
 * @version imkarl 2017-03
 */
public class SafeWebView extends WebView {

    public SafeWebView(Context context) {
        super(context);
        init(context);
    }
    public SafeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }
    public SafeWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public SafeWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        // 启用JavaScript
        this.getSettings().setJavaScriptEnabled(true);
        // 让网页自动适应屏幕宽度
        this.getSettings().setUseWideViewPort(true);
        this.getSettings().setLoadWithOverviewMode(true);
    }

    public void injectJsCallJava(SafeWebChromeClient client) {
        super.setWebChromeClient(client);
    }
    public void injectJsCallJava(String injectedName, Class injectedClass) {
        injectJsCallJava(new SafeWebChromeClient(injectedName, injectedClass));
    }

}
