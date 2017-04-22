package com.copy.jianshuapp.uilayer.login.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.InputType;
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
import com.copy.jianshuapp.common.ObjectUtils;
import com.copy.jianshuapp.common.rx.RxKeyboard;
import com.copy.jianshuapp.common.rx.RxTextViews;
import com.copy.jianshuapp.exception.ApiException;
import com.copy.jianshuapp.exception.ExceptionUtils;
import com.copy.jianshuapp.modellayer.local.SettingsUtils;
import com.copy.jianshuapp.modellayer.remote.ErrorCode;
import com.copy.jianshuapp.modellayer.repository.UserRepository;
import com.copy.jianshuapp.uilayer.base.BaseFragment;
import com.copy.jianshuapp.uilayer.login.activity.LoginActivity;
import com.copy.jianshuapp.uilayer.plugin.register.OnSendCaptchaListener;
import com.copy.jianshuapp.uilayer.plugin.register.RegisterPlugin;
import com.copy.jianshuapp.uilayer.widget.dialog.Alerts;
import com.copy.jianshuapp.utils.CheckFormatUtils;
import com.copy.jianshuapp.utils.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import jianshu.widget.LocalLinkMovementMethod;
import jianshu.widget.TextViewFixTouchConsume;

/**
 * 用户注册
 * @version imkarl 2017-03
 */
public class RegisterFragment extends BaseFragment implements View.OnFocusChangeListener, View.OnClickListener {

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
    private boolean mShowPassword;

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

        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(it -> {
                    // 预加载，提高响应速度
                    RegisterPlugin.inject(getActivity()).destroy();
                });
    }

    private void initView() {
        // 提示信息
        mTvUserAgreement.setMovementMethod(LocalLinkMovementMethod.getInstance());
        mTvUserAgreement.setTextViewHTML("点击&#160;\"注册简书帐号\"&#160;即表示你同意并愿意遵守简书 <a href=\"http://www.jianshu.com/FormBody/c44d171298ce\">用户协议</a> 和 <a href=\"http://www.jianshu.com/FormBody/2ov8x3\">隐私政策</a>");
        RxKeyboard.stateChange(getActivity())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(isOpen -> {
                    mTvUserAgreement.setVisibility(isOpen ? View.VISIBLE : View.GONE);
                });

        // 监听输入
        RxTextViews.textChanges(mEtNickname, mEtTel, mEtPassword)
                .subscribe(it -> {
                    String nickname = mEtNickname.getText().toString().trim();
                    String phone = mEtTel.getText().toString().trim();
                    String password = mEtPassword.getText().toString().trim();

                    if (!ObjectUtils.isEmpty(nickname)
                            && !ObjectUtils.isEmpty(phone)
                            && !ObjectUtils.isEmpty(password)) {
                        mBtnRegister1.setEnabled(true);
                    } else {
                        mBtnRegister1.setEnabled(false);
                    }
                });

        RxView.focusChanges(mEtNickname)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(isFocus -> onFocusChange(mEtNickname, isFocus));
        RxView.focusChanges(mEtTel)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(isFocus -> onFocusChange(mEtTel, isFocus));
        RxView.focusChanges(mEtPassword)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(isFocus -> onFocusChange(mEtPassword, isFocus));

        mIvDeleteNickname.setOnClickListener(this);
        mIvDeleteTel.setOnClickListener(this);
        mIvDeletePass.setOnClickListener(this);

        // 关闭按钮
        mIvClose.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput();
            getActivity().onBackPressed();
        });

        // 跳转到登录
        mBtnLogin.setOnClickListener(v -> ((LoginActivity)getActivity()).showLoginFragment());

        // 注册按钮
        RxView.clicks(mBtnRegister1)
                .filter(it -> checkInputs(true))
                .throttleFirst(1, TimeUnit.SECONDS)
                .flatMap(it -> {
                    String nickname = mEtNickname.getText().toString().trim();
                    String phone = mEtTel.getText().toString().trim();
                    // 检查用户名、手机号是否可用
                    return UserRepository.checkNickname(nickname)
                            .flatMap(success -> UserRepository.checkPhone(phone))
                            .observeOn(AndroidSchedulers.mainThread())
                            .onErrorResumeNext(throwable -> {
                                ToastUtils.show(ExceptionUtils.getDescription(throwable));
                                return Observable.just(false);
                            });
                })
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(usable -> {
                    // 填写资料不合法
                    if (!usable) {
                        return;
                    }

                    // 关闭键盘
                    KeyboardUtils.hideSoftInput();

                    String nickname = mEtNickname.getText().toString().trim();
                    String phone = mEtTel.getText().toString().trim();
                    String password = mEtPassword.getText().toString().trim();

                    // 载入注册组件，完成注册
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
                                    // 输入验证码，并提交注册
                                    Alerts.prompt("手机验证", "验证码已发送到"+phone+"，请注意查收", "请输入验证码")
                                            .compose(bindToLifecycle())
                                            .flatMap(captcha -> mRegisterPlugin.submit(captcha))
                                            .observeOn(AndroidSchedulers.mainThread())
                                            .subscribe(success -> {
                                                ToastUtils.show("注册成功");
                                                SettingsUtils.put(SettingsUtils.HAS_REGIST, true);
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
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        mIvDeleteNickname.setVisibility(View.GONE);
        mIvDeleteTel.setVisibility(View.GONE);
        mIvDeletePass.setVisibility(View.GONE);

        if (v == mEtNickname) {
            mIvDeleteNickname.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        } else if (v == mEtTel) {
            mIvDeleteTel.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        } else if (v == mEtPassword) {
            mIvDeletePass.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mIvDeleteNickname) {
            mEtNickname.getText().clear();
        } else if (v == mIvDeleteTel) {
            mEtTel.getText().clear();
        } else if (v == mIvDeletePass) {
            if (!this.mShowPassword) {
                this.mEtPassword.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                this.mIvDeletePass.setImageResource(R.drawable.icon_password_eyes_invisible);
            } else {
                this.mEtPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                this.mIvDeletePass.setImageResource(R.drawable.icon_password_eyes_visible);
            }
            this.mShowPassword = !this.mShowPassword;
            this.mEtPassword.setSelection(this.mEtPassword.getText().length());
        }
    }

    private boolean checkInputs(boolean tips) {
        String nickname = mEtNickname.getText().toString().trim();
        if (!CheckFormatUtils.isNickname(nickname)) {
            if (tips) {
                Alerts.tips("昵称 格式无效");
            }
            return false;
        }

        String phone = mEtTel.getText().toString().trim();
        if (!CheckFormatUtils.isPhone(phone)) {
            if (tips) {
                Alerts.tips("手机号 格式无效");
            }
            return false;
        }

        // 密码不去掉前后空格
        String password = mEtPassword.getText().toString();
        if (!CheckFormatUtils.isPassword(password)) {
            if (tips) {
                Alerts.tips("密码 格式无效");
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
