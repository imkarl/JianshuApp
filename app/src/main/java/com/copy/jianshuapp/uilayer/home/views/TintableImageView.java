package com.copy.jianshuapp.uilayer.home.views;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 可改变颜色的ImageView
 * @author alafighting 2016-01
 */
public class TintableImageView extends ImageView {

    public TintableImageView(Context context) {
        super(context);
    }

    public TintableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TintableImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TintableImageView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


}
