package com.copy.jianshuapp.uilayer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.copy.jianshuapp.R;
import com.copy.jianshuapp.modellayer.local.SettingsUtils;
import com.copy.jianshuapp.utils.Constants;

/**
 * @version JianShu 2017-04
 */
public class SmartSwitchButton extends RelativeLayout implements View.OnClickListener, Animation.AnimationListener {
    private boolean isChecked = false;
    private boolean isPlayingAnim = false;
    private ImageView mCircle;
    private int mCircleOffDayResId;
    private int mCircleOffNightResId;
    private int mCircleOnResId;
    private Context mContext;
    private ImageView mLine;
    private int mLineOnResId;
    private OnCheckedChangedListener mListener;
    private Constants.JSTheme theme;

    public interface OnCheckedChangedListener {
        void onCheckedChange(boolean z, SmartSwitchButton smartSwitchButton);
    }

    public SmartSwitchButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode()) {
            initView(context);
        }
    }

    public SmartSwitchButton(Context context) {
        super(context);
        if (!isInEditMode()) {
            initView(context);
        }
    }

    private void initView(Context ctx) {
        this.mContext = ctx;
        this.theme = SettingsUtils.getTheme();
        View.inflate(ctx, R.layout.layout_smart_switch_button, this);
        this.mLine = (ImageView) findViewById(R.id.img_line);
        this.mCircle = (ImageView) findViewById(R.id.img_circle);
        this.mLineOnResId = R.drawable.butten_line_open;
        this.mCircleOnResId = R.drawable.btn_switch_open;
        this.mCircleOffDayResId = R.drawable.btn_switch_close_normal;
        this.mCircleOffNightResId = R.drawable.btn_switch_close_night;
        setOnClickListener(this);
    }

    public void setChecked(boolean b) {
        this.isChecked = b;
        if (b) {
            this.mLine.setImageDrawable(this.mContext.getResources().getDrawable(this.mLineOnResId));
            this.mCircle.setImageDrawable(this.mContext.getResources().getDrawable(this.mCircleOnResId));
            LayoutParams layoutParams = new LayoutParams(-2, -2);
            layoutParams.addRule(11);
            layoutParams.addRule(15);
            this.mCircle.setLayoutParams(layoutParams);
            return;
        }
        if (this.theme == Constants.JSTheme.DAY) {
            this.mLine.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.btn_line_close_normal));
            this.mCircle.setImageDrawable(this.mContext.getResources().getDrawable(this.mCircleOffDayResId));
        } else {
            this.mLine.setImageDrawable(this.mContext.getResources().getDrawable(R.drawable.btn_line_close_night));
            this.mCircle.setImageDrawable(this.mContext.getResources().getDrawable(this.mCircleOffNightResId));
        }
        LayoutParams layoutParams = new LayoutParams(-2, -2);
        layoutParams.addRule(9);
        layoutParams.addRule(15);
        this.mCircle.setLayoutParams(layoutParams);
    }

    public void onClick(View view) {
        if (!this.isPlayingAnim) {
            startAnim();
        }
    }

    private void startAnim() {
        this.isPlayingAnim = true;
        int distance = this.mLine.getMeasuredWidth() - this.mCircle.getMeasuredWidth();
        TranslateAnimation anim = new TranslateAnimation(0.0f, this.isChecked ? (float) (-distance) : (float) distance, 0.0f, 0.0f);
        anim.setDuration(40);
        anim.setFillAfter(false);
        anim.setAnimationListener(this);
        this.mCircle.startAnimation(anim);
    }

    public void setOnCheckedChangedListener(OnCheckedChangedListener listener) {
        this.mListener = listener;
    }

    public void onAnimationStart(Animation animation) {
    }

    public void onAnimationEnd(Animation animation) {
        setChecked(!this.isChecked);
        if (this.mListener != null) {
            this.mListener.onCheckedChange(this.isChecked, this);
        }
        this.isPlayingAnim = false;
    }

    public void onAnimationRepeat(Animation animation) {
    }

    public void onSwitchTheme(Constants.JSTheme theme) {
        this.theme = theme;
        setChecked(isChecked());
    }

    public boolean isChecked() {
        return this.isChecked;
    }
}
