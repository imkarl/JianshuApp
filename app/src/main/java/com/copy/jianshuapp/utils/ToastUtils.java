package com.copy.jianshuapp.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

import com.copy.jianshuapp.common.AppUtils;

/**
 * Toast辅助工具
 * @author alafighting 2016-02
 * @author imkarl 2017-03 去除Context参数
 */
public class ToastUtils {
    private static Toast mToast = null;

    private ToastUtils() {
    }


    /*
     * 以下方法自动调用Toast的show()方法
     */
    public static void show(CharSequence text, boolean duration, boolean update) {
        makeText(text, duration, update).show();
    }
    public static void show(int resId, boolean duration, boolean update) {
        makeText(resId, duration, update).show();
    }

    public static void show(CharSequence text, boolean duration) {
        show(text, duration, true);
    }
    public static void show(int resId, boolean duration) {
        show(resId, duration, true);
    }

    public static void show(CharSequence text) {
        show(text, false);
    }
    public static void show(int resId) {
        show(resId, false);
    }

    public static void show(CharSequence format, boolean duration, Object... args) {
        show((format==null ? null : String.format(format.toString(), args)), duration);
    }
    public static void show(int formatResourceId, boolean duration, Object... args) {
        show(AppUtils.getContext().getResources().getString(formatResourceId, args), duration);
    }

    public static void show(CharSequence format, Object... args) {
        show(format, false, args);
    }
    public static void show(int formatResourceId, Object... args) {
        show(formatResourceId, false, args);
    }





    /**
     * 创建Toast实例
     * @param resId 文本内容的资源ID
     * @param duration 持续显示时长（true:长时间，false:短时间）
     * @param update 立即显示新的Toast内容，无需等待上个Toast消失（true:立即，false:队列等待）
     * @return {@link Toast}
     */
    private static Toast makeText(int resId, boolean duration, boolean update) {
        return makeText(AppUtils.getContext().getText(resId), duration, update);
    }
    /**
     * 创建Toast实例
     * @param text 文本内容
     * @param duration 持续显示时长（true:长时间，false:短时间）
     * @param update 立即显示新的Toast内容，无需等待上个Toast消失（true:立即，false:队列等待）
     * @return {@link Toast}
     */
    @SuppressLint("ShowToast")
    private static Toast makeText(CharSequence text, boolean duration, boolean update) {
        Context context = AppUtils.getContext();
        if (context == null) {
            throw new IllegalArgumentException("'context' can not be NULL.");
        }
        if (text == null) {
            throw new IllegalArgumentException("'text' can not be NULL.");
        }

        if (update) {
            if (mToast != null) {
                mToast.setText(text);
                mToast.setDuration(duration ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
            } else {
                setToast(Toast.makeText(context, text, duration ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT));
            }
            return mToast;
        } else {
            return Toast.makeText(context, text, duration ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);
        }
    }



    /**
     * 设置当前Toast，如果已存在，则先销毁
     */
    public static void setToast(Toast toast) {
        if (mToast != null)
            mToast.cancel();
        mToast = toast;
    }

    /**
     * 销毁Toast
     */
    public static void cancel() {
        if (mToast != null)
            mToast.cancel();
        mToast = null;
    }

}
