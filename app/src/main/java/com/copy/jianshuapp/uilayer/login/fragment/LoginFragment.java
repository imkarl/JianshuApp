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
import com.copy.jianshuapp.common.AppUtils;
import com.copy.jianshuapp.common.KeyboardUtils;
import com.copy.jianshuapp.common.LogUtils;
import com.copy.jianshuapp.common.ObjectUtils;
import com.copy.jianshuapp.common.rx.RxTextViews;
import com.copy.jianshuapp.exception.ExceptionUtils;
import com.copy.jianshuapp.modellayer.repository.UserRepository;
import com.copy.jianshuapp.uilayer.base.BaseFragment;
import com.copy.jianshuapp.uilayer.login.activity.LoginActivity;
import com.copy.jianshuapp.uilayer.widget.dialog.Alerts;
import com.copy.jianshuapp.utils.CheckFormatUtils;
import com.copy.jianshuapp.utils.ToastUtils;
import com.jakewharton.rxbinding2.view.RxView;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 用户登录
 *
 * @version imkarl 2017-03
 */
public class LoginFragment extends BaseFragment
        implements View.OnFocusChangeListener, View.OnClickListener {
    @Bind(R.id.iv_close)
    ImageView mIvClose;
    @Bind(R.id.tv_last_account)
    TextView mTvLastAccount;
    @Bind(R.id.et_account)
    EditText mEtAccount;
    @Bind(R.id.iv_delete_account)
    ImageView mIvDeleteAccount;
    @Bind(R.id.et_password)
    EditText mEtPassword;
    @Bind(R.id.iv_delete_pass)
    ImageView mIvDeletePass;
    @Bind(R.id.btn_login)
    Button mBtnLogin;
    @Bind(R.id.tv_forget_pass_bottom)
    TextView mTvForgetPassBottom;
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
    @Bind(R.id.btn_register)
    TextView mBtnRegister;
    @Bind(R.id.view_line)
    View mViewLine;
    @Bind(R.id.tv_go_mainpage)
    TextView mTvGoMainpage;
    @Bind(R.id.view_line_2)
    View mViewLine2;
    @Bind(R.id.tv_login_error)
    TextView mTvLoginError;
    @Bind(R.id.ll_switch)
    LinearLayout mLlSwitch;
    @Bind(R.id.ll_detect)
    LinearLayout mLlDetect;

    private boolean mShowPassword;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 监听输入
        RxTextViews.textChanges(mEtAccount, mEtPassword)
                .subscribe(it -> {
                    String account = mEtAccount.getText().toString().trim();
                    String password = mEtPassword.getText().toString().trim();

                    if (!ObjectUtils.isEmpty(account)
                            && !ObjectUtils.isEmpty(password)) {
                        mBtnLogin.setEnabled(true);
                    } else {
                        mBtnLogin.setEnabled(false);
                    }
                });

        RxView.focusChanges(mEtAccount)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(isFocus -> onFocusChange(mEtAccount, isFocus));
        RxView.focusChanges(mEtPassword)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(isFocus -> onFocusChange(mEtPassword, isFocus));

        mIvDeleteAccount.setOnClickListener(this);
        mIvDeletePass.setOnClickListener(this);

        // 关闭按钮
        mIvClose.setOnClickListener(v -> {
            KeyboardUtils.hideSoftInput();
            getActivity().onBackPressed();
        });

        // 跳转到注册
        mBtnRegister.setOnClickListener(v -> ((LoginActivity)getActivity()).showRegisterFragment());

        // 登录
        RxView.clicks(mBtnLogin)
                .filter(it -> checkInputs(true))
                .throttleFirst(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .compose(bindToLifecycle())
                .subscribe(it -> {
                    // 关闭键盘
                    KeyboardUtils.hideSoftInput();

                    String account = mEtAccount.getText().toString().trim();
                    String password = mEtPassword.getText().toString().trim();

                    // 提交
                    UserRepository.login(account, password)
                            .observeOn(AndroidSchedulers.mainThread())
                            .compose(bindToLifecycle())
                            .subscribe(user -> {
                                // 登录成功
                                AppUtils.setLoginAccount(user);
                                getActivity().onBackPressed();
                            }, err -> {
                                LogUtils.e(err);
                                Alerts.tips(ExceptionUtils.getDescription(err));
                            });
                });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        mIvDeleteAccount.setVisibility(View.GONE);
        mIvDeletePass.setVisibility(View.GONE);

        if (v == mEtAccount) {
            mIvDeleteAccount.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        } else if (v == mEtPassword) {
            mIvDeletePass.setVisibility(hasFocus ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        if (v == mIvDeleteAccount) {
            mEtAccount.getText().clear();
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
        String account = mEtAccount.getText().toString().trim();
        if (!CheckFormatUtils.isEmail(account) && !CheckFormatUtils.isPhone(account)) {
            if (tips) {
                Alerts.tips("输入登录名格式无效，请输入注册/绑定的邮件地址或手机号码。");
            }
            return false;
        }

        String password = mEtPassword.getText().toString().trim();
        if (!CheckFormatUtils.isPassword(password)) {
            if (tips) {
                Alerts.tips("密码 格式无效");
            }
            return false;
        }

        return true;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

}
