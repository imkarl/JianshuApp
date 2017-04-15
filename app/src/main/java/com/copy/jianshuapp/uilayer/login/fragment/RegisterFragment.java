package com.copy.jianshuapp.uilayer.login.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.copy.jianshuapp.R;
import com.copy.jianshuapp.common.KeyboardUtils;
import com.copy.jianshuapp.common.LogUtils;
import com.copy.jianshuapp.common.rx.RxTextViews;
import com.copy.jianshuapp.exception.ApiException;
import com.copy.jianshuapp.exception.ExceptionUtils;
import com.copy.jianshuapp.modellayer.remote.ErrorCode;
import com.copy.jianshuapp.modellayer.repository.UserRepository;
import com.copy.jianshuapp.uilayer.base.BaseFragment;
import com.copy.jianshuapp.uilayer.login.activity.LoginActivity;
import com.copy.jianshuapp.uilayer.plugin.register.OnSendCaptchaListener;
import com.copy.jianshuapp.uilayer.plugin.register.RegisterPlugin;
import com.copy.jianshuapp.uilayer.widget.dialog.Alerts;
import com.copy.jianshuapp.utils.CheckFormatUtils;
import com.copy.jianshuapp.utils.ToastUtils;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import jianshu.widget.TextViewFixTouchConsume;

/**
 * 用户注册
 * @version imkarl 2017-03
 */
public class RegisterFragment extends BaseFragment {

    @Bind(R.id.et_nickname)
    EditText mEtNickname;
    @Bind(R.id.iv_delete_nickname)
    ImageView mIvDeleteNickname;
    @Bind(R.id.et_tel)
    EditText mEtTel;
    @Bind(R.id.iv_delete_tel)
    ImageView mIvDeleteTel;
    @Bind(R.id.et_password)
    EditText mEtPassword;
    @Bind(R.id.iv_delete_pass)
    ImageView mIvDeletePass;
    @Bind(R.id.tv_user_agreement)
    TextViewFixTouchConsume mTvUserAgreement;
    @Bind(R.id.btn_register_1)
    Button mBtnRegister1;
    @Bind(R.id.text_social_tag)
    TextView mTextSocialTag;
    @Bind(R.id.iv_wechat)
    RoundedImageView mIvWechat;
    @Bind(R.id.iv_qq)
    RoundedImageView mIvQq;
    @Bind(R.id.iv_weibo)
    RoundedImageView mIvWeibo;
    @Bind(R.id.tv_others)
    TextView mTvOthers;
    @Bind(R.id.iv_douban)
    RoundedImageView mIvDouban;
    @Bind(R.id.iv_google)
    RoundedImageView mIvGoogle;
    @Bind(R.id.linear_social)
    LinearLayout mLinearSocial;
    @Bind(R.id.btn_login)
    TextView mBtnLogin;
    @Bind(R.id.view_line_2)
    View mViewLine2;
    @Bind(R.id.tv_go_mainpage)
    TextView mTvGoMainpage;
    @Bind(R.id.ll_detect)
    LinearLayout mLlDetect;
    @Bind(R.id.iv_close)
    ImageView mIvClose;

    private RegisterPlugin mRegisterPlugin;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View rootView = inflater.inflate(R.layout.fragment_register, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        // 预加载，提高响应速度
        RegisterPlugin.inject(getActivity()).destroy();
    }

    private void initView() {
        // 监听输入
        RxTextViews.textChanges(mEtNickname, mEtTel, mEtPassword)
                .subscribe(it -> {
                    if (checkInputs(false)) {
                        mBtnRegister1.setEnabled(true);
                    } else {
                        mBtnRegister1.setEnabled(false);
                    }
                });

        // 关闭按钮
        mIvClose.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput(getActivity());
            getActivity().onBackPressed();
        });

        // 注册按钮
        mBtnRegister1.setOnClickListener(v -> {
            // 关闭键盘
            KeyboardUtils.hideSoftInput(getActivity());

            String nickname = mEtNickname.getText().toString().trim();
            String phone = mEtTel.getText().toString().trim();
            String password = mEtPassword.getText().toString().trim();

            // 检查用户名、手机号是否可用
            UserRepository.checkNickname(nickname)
                    .flatMap(it -> UserRepository.checkPhone(phone))
                    .observeOn(AndroidSchedulers.mainThread())
                    .compose(bindToLifecycle())
                    .subscribe(usable -> {
                        mRegisterPlugin = RegisterPlugin.inject(getActivity())
                                .addOnLoadFinish(() -> {
                                    // 确保html渲染完成
                                    Observable.timer(300, TimeUnit.MILLISECONDS)
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(it -> {
                                                mRegisterPlugin.sendCaptcha(nickname, phone, password);
                                            });
                                })
                                .setOnSendCaptchaListener(new OnSendCaptchaListener() {
                                    @Override
                                    public void onSendCaptcha() {
                                        Alerts.prompt("手机验证", "验证码已发送到"+phone+"，请注意查收", "请输入验证码")
                                                .compose(bindToLifecycle())
                                                .flatMap(captcha -> mRegisterPlugin.submit(captcha))
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(success -> {
                                                    ToastUtils.show("注册成功");
                                                    mRegisterPlugin.destroy();
                                                    // 切换成登录界面
                                                    showLoginFragment();
                                                }, err -> {
                                                    LogUtils.e(err);
                                                    ToastUtils.show(ExceptionUtils.getDescription(err));

                                                    if (err instanceof ApiException) {
                                                        // 验证码错误
                                                        if (((ApiException) err).contains(ErrorCode.PARAMETER_CAPTCHA_WRONG)) {
                                                            onSendCaptcha();
                                                            return;
                                                        }
                                                    }
                                                    mRegisterPlugin.destroy();
                                                });
                                    }
                                });
                    }, err -> {
                        LogUtils.e(err);
                        ToastUtils.show(ExceptionUtils.getDescription(err));
                    });
        });
    }

    private boolean checkInputs(boolean tips) {
        String nickname = mEtNickname.getText().toString().trim();
        if (!CheckFormatUtils.isNickname(nickname)) {
            if (tips) {
                ToastUtils.show("昵称 格式无效");
            }
            return false;
        }

        String phone = mEtTel.getText().toString().trim();
        if (!CheckFormatUtils.isPhone(phone)) {
            if (tips) {
                ToastUtils.show("手机号 格式无效");
            }
            return false;
        }

        // 密码不去掉前后空格
        String password = mEtPassword.getText().toString();
        if (!CheckFormatUtils.isPassword(password)) {
            if (tips) {
                ToastUtils.show("密码 格式无效");
            }
            return false;
        }

        return true;
    }

    private void showLoginFragment() {
        ((LoginActivity)getActivity()).showLoginFragment();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
        if (mRegisterPlugin != null) {
            mRegisterPlugin.destroy();
            mRegisterPlugin = null;
        }
    }

}
