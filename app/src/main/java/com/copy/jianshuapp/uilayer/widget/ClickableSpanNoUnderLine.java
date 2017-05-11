package com.copy.jianshuapp.uilayer.widget;

import android.text.TextPaint;
import android.text.style.ClickableSpan;

/**
 * 不显示下划线的ClickableSpan
 */
public abstract class ClickableSpanNoUnderLine extends ClickableSpan {
    public void updateDrawState(TextPaint ds) {
        ds.setColor(ds.linkColor);
        ds.setUnderlineText(false);
    }
}
