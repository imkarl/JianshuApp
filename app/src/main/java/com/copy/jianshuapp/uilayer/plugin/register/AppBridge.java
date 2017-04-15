package com.copy.jianshuapp.uilayer.plugin.register;

import android.webkit.WebView;

import com.copy.jianshuapp.common.rx.RxBus;

import cn.imkarl.urlbuilder.UrlQuery;

/**
 * App和JS交互的接口类
 * @version imkarl 2017-04
 */
class AppBridge {
    public static String NAME = "AppBridge";

    public static void updateStatus(final WebView view, final boolean isSending) {
        RxBus.post(new RegisterPlugin.RegisterPluginEvent(RegisterPlugin.EventType.SEND_CAPTCHA_STATUS, isSending));
    }

    public static void onGetFormField(final WebView view, final String fields) {
        RxBus.post(new RegisterPlugin.RegisterPluginEvent(RegisterPlugin.EventType.FORM_FIELD, UrlQuery.parse(fields)));
    }

}
