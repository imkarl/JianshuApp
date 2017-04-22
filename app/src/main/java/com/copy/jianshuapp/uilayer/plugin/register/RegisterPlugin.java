package com.copy.jianshuapp.uilayer.plugin.register;

import android.app.Activity;
import android.view.ViewGroup;

import com.copy.jianshuapp.JSApi;
import com.copy.jianshuapp.common.rx.RxBus;
import com.copy.jianshuapp.modellayer.repository.UserRepository;
import com.copy.jianshuapp.uilayer.plugin.OnLoadFinish;
import com.copy.jianshuapp.uilayer.plugin.Plugin;
import com.copy.jianshuapp.utils.DisplayUtils;
import com.copy.jianshuapp.utils.ToastUtils;

import java.util.concurrent.TimeUnit;

import cn.imkarl.urlbuilder.UrlQuery;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

/**
 * 注册插件
 * @version imkarl 2017-04
 */
public final class RegisterPlugin extends Plugin {
    public static RegisterPlugin inject(Activity activity) {
        return Plugin.inject(activity, RegisterPlugin.class);
    }

    private static final int VIEW_WIDTH = (int) (354 * DisplayUtils.getScaledDensity());
    private static final int VIEW_HEIGHT = (int) (286 * DisplayUtils.getScaledDensity());

    private static final String URL_SUBMIT_REIGSTER = "https://www.jianshu.com/users/register";

    /** 最后一次发送验证码的时间 */
    private long mLastSendCaptcha = -1;

    private Disposable mDisposableSendCaptcha;

    private OnSendCaptchaListener mOnSendCaptchaListener;

    private String nickname, phone, password;

    protected void init(ViewGroup parent) {
        super.init(parent, VIEW_WIDTH, VIEW_HEIGHT);
        super.setOverlay(true);
        super.setLoading(true);

        mDisposableSendCaptcha = RxBus.register(RegisterPluginEvent.class)
                .filter(event -> event.type == EventType.SEND_CAPTCHA_STATUS)
                .map(event -> (boolean)event.data)
                .filter(isSending -> isSending)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isSending -> {
                    mLastSendCaptcha = System.currentTimeMillis();
                    setLoading(true);
                    onSendCaptcha();
                });
    }

    @Override
    protected String getInjectedName() {
        return AppBridge.NAME;
    }

    @Override
    protected Class getInjectedClass() {
        return AppBridge.class;
    }

    @Override
    protected String getCurrentLink() {
        return JSApi.WEBPAGE_REGISTER;
    }

    /**
     * 发送验证码
     * @param nickname 昵称
     * @param phone 手机号
     * @param password 密码
     */
    public void sendCaptcha(String nickname, String phone, String password) {
        final long currentTime = System.currentTimeMillis();
        if (currentTime - mLastSendCaptcha < 60*1000) {
            ToastUtils.show("您刚刚发送过一条验证码，如果一分钟内没有收到，您可以再次发送。");
            return;
        }

        this.nickname = nickname;
        this.phone = phone;
        this.password = password;

        if (isPageFinish()) {
            // 显示灰色覆盖层
            addHtmlOverlay();

            // 填充表单
            runJS("$('[name=\"user[nickname]\"]').val('" + nickname + "');"
                    + "$('[name=\"user[mobile_number]\"]').val('" + phone + "');"
                    + "$('[name=\"user[password]\"]').val('" + password + "');");

            // 显示验证码输入框
            runJS("$('.security-up-code').removeClass('hide');" +
                    "function showSendCode() {" +
                        "var text = $($('.js-send-code-button')[0]).text();" +
                        "var isSending = text.startsWith('重新发送(');" +
                        "if (isSending) {" +
                            "clearInterval(_showSendCode);"+
                        "} else {" +
                            "$('.btn-up-resend')[1].click();"+
                        "}" +
                    "}" +
                    "var _showSendCode = window.setInterval(\"showSendCode()\", 1000);");
            // 不显示关闭按钮
            runJS("$('.gt_popup_cross').hide();");

            // 监听验证码是否发送
            runJS("function getSendStatus() {" +
                        "var text = $($('.js-send-code-button')[0]).text();" +
                        "var isSending = text.startsWith('重新发送(');" +
                        "if (isSending) {" +
                            AppBridge.NAME+".updateStatus(isSending);" +
                            "clearInterval(_getSendStatus);"+
                        "}" +
                    "}" +
                    "var _getSendStatus = window.setInterval(\"getSendStatus()\", 300);");

            Observable.timer(300, TimeUnit.MILLISECONDS).subscribe(it -> setLoading(false));
        } else {
            addOnLoadFinish(() -> sendCaptcha(nickname, phone, password));
        }
    }

    /**
     * 提交注册
     * @param captcha 验证码
     */
    public Observable<Boolean> submit(String captcha) {
        // 获取表单待提交数据
        runJS(AppBridge.NAME+".onGetFormField($('#new_user').serialize());");
        // 调用API接口
        return RxBus.register(RegisterPluginEvent.class)
                .filter(event -> event.type == EventType.FORM_FIELD)
                .map(event -> (UrlQuery)event.data)
                .flatMap(query -> UserRepository.register(nickname, phone, password, captcha, query));
    }

    private void onSendCaptcha() {
        if (mOnSendCaptchaListener != null) {
            mOnSendCaptchaListener.onSendCaptcha();
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public RegisterPlugin addOnLoadFinish(OnLoadFinish listener) {
        return super.addOnLoadFinish(listener);
    }
    public RegisterPlugin setOnSendCaptchaListener(OnSendCaptchaListener listener) {
        mOnSendCaptchaListener = listener;
        return this;
    }

    @Override
    public void destroy() {
        super.destroy();
        if (mDisposableSendCaptcha != null && !mDisposableSendCaptcha.isDisposed()) {
            mDisposableSendCaptcha.dispose();
            mDisposableSendCaptcha = null;
        }
    }

    static class RegisterPluginEvent {
        RegisterPlugin.EventType type;
        Object data;
        RegisterPluginEvent(RegisterPlugin.EventType type, Object data) {
            this.type = type;
            this.data = data;
        }
    }
    enum EventType {
        // 发送验证码的状态
        SEND_CAPTCHA_STATUS,
        // 表单的所有字段
        FORM_FIELD
    }

}
