package com.copy.jianshuapp.uilayer.widget.adapter;

import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v4.content.ContextCompat;
import android.text.util.Linkify;
import android.util.SparseArray;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CheckedTextView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * ItemView处理辅助类
 * @version imkarl 2016-10
 */
public class ItemViewHelper {

    private final SparseArray<View> views = new SparseArray<>();
    private View convertView;
    private int position;

    protected ItemViewHelper(View view) {
        this.convertView = view;
    }

    public View getConvertView() {
        return this.convertView;
    }

    protected void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public ItemViewHelper setText(int viewId, CharSequence value) {
        TextView view = (TextView)this.getView(viewId);
        view.setText(value);
        return this;
    }

    public ItemViewHelper setText(int viewId, @StringRes int strId) {
        TextView view = (TextView)this.getView(viewId);
        view.setText(strId);
        return this;
    }

    public ItemViewHelper setImageResource(int viewId, @DrawableRes int imageResId) {
        ImageView view = (ImageView)this.getView(viewId);
        view.setImageResource(imageResId);
        return this;
    }

    public ItemViewHelper setBackgroundColor(int viewId, int color) {
        View view = this.getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public ItemViewHelper setBackgroundRes(int viewId, @DrawableRes int backgroundRes) {
        View view = this.getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public ItemViewHelper setTextColor(int viewId, @ColorInt int textColor) {
        TextView view = this.getView(viewId);
        view.setTextColor(textColor);
        return this;
    }
    public ItemViewHelper setTextColorRes(int viewId, @ColorRes int textColorRes) {
        TextView view = this.getView(viewId);
        view.setTextColor(ContextCompat.getColor(view.getContext(), textColorRes));
        return this;
    }

    public ItemViewHelper setImageDrawable(int viewId, Drawable drawable) {
        ImageView view = this.getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public ItemViewHelper setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView view = this.getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public ItemViewHelper setAlpha(int viewId, float value) {
        if(Build.VERSION.SDK_INT >= 11) {
            this.getView(viewId).setAlpha(value);
        } else {
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0L);
            alpha.setFillAfter(true);
            this.getView(viewId).startAnimation(alpha);
        }

        return this;
    }

    public ItemViewHelper setVisible(int viewId, boolean visible) {
        View view = this.getView(viewId);
        view.setVisibility(visible?View.VISIBLE:View.GONE);
        return this;
    }

    public ItemViewHelper linkify(int viewId) {
        TextView view = this.getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public ItemViewHelper setTypeface(int viewId, Typeface typeface) {
        TextView view = this.getView(viewId);
        view.setTypeface(typeface);
        view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        return this;
    }

    public ItemViewHelper setTypeface(Typeface typeface, int... viewIds) {
        int[] var3 = viewIds;
        int var4 = viewIds.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            int viewId = var3[var5];
            TextView view = this.getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }

        return this;
    }

    public ItemViewHelper setProgress(int viewId, int progress) {
        ProgressBar view = this.getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public ItemViewHelper setProgress(int viewId, int progress, int max) {
        ProgressBar view = this.getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public ItemViewHelper setMax(int viewId, int max) {
        ProgressBar view = this.getView(viewId);
        view.setMax(max);
        return this;
    }

    public ItemViewHelper setRating(int viewId, float rating) {
        RatingBar view = this.getView(viewId);
        view.setRating(rating);
        return this;
    }

    public ItemViewHelper setRating(int viewId, float rating, int max) {
        RatingBar view = this.getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public ItemViewHelper setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = this.getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public ItemViewHelper setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = this.getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }

    public ItemViewHelper setOnLongClickListener(int viewId, View.OnLongClickListener listener) {
        View view = this.getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    public ItemViewHelper setOnItemClickListener(int viewId, AdapterView.OnItemClickListener listener) {
        AdapterView view = this.getView(viewId);
        view.setOnItemClickListener(listener);
        return this;
    }

    public ItemViewHelper setOnItemLongClickListener(int viewId, AdapterView.OnItemLongClickListener listener) {
        AdapterView view = this.getView(viewId);
        view.setOnItemLongClickListener(listener);
        return this;
    }

    public ItemViewHelper setOnItemSelectedClickListener(int viewId, AdapterView.OnItemSelectedListener listener) {
        AdapterView view = this.getView(viewId);
        view.setOnItemSelectedListener(listener);
        return this;
    }

    public ItemViewHelper setOnCheckedChangeListener(int viewId, CompoundButton.OnCheckedChangeListener listener) {
        CompoundButton view = this.getView(viewId);
        view.setOnCheckedChangeListener(listener);
        return this;
    }

    public ItemViewHelper setTag(int viewId, Object tag) {
        View view = this.getView(viewId);
        view.setTag(tag);
        return this;
    }

    public ItemViewHelper setTag(int viewId, int key, Object tag) {
        View view = this.getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public ItemViewHelper setChecked(int viewId, boolean checked) {
        View view = this.getView(viewId);
        if(view instanceof CompoundButton) {
            ((CompoundButton)view).setChecked(checked);
        } else if(view instanceof CheckedTextView) {
            ((CheckedTextView)view).setChecked(checked);
        }

        return this;
    }

    public ItemViewHelper setAdapter(int viewId, Adapter adapter) {
        AdapterView view = this.getView(viewId);
        view.setAdapter(adapter);
        return this;
    }

    public <T extends View> T getView(int viewId) {
        View view = this.views.get(viewId);
        if(view == null) {
            view = this.convertView.findViewById(viewId);
            this.views.put(viewId, view);
        }
        return (T) view;
    }

}
