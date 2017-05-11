package com.copy.jianshuapp.uilayer.widget.dialog;

import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.InputType;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.copy.jianshuapp.common.ActivityLifcycleManager;
import com.copy.jianshuapp.common.AppUtils;
import com.copy.jianshuapp.common.DisplayInfo;
import com.copy.jianshuapp.common.LogUtils;
import com.copy.jianshuapp.common.ObjectUtils;
import com.copy.jianshuapp.exception.ArgumentException;
import com.copy.jianshuapp.uilayer.listeners.OnItemClickListener;
import com.labo.kaji.relativepopupwindow.RelativePopupWindow;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 弹窗相关
 * @version imkarl 2017-04
 */
public class Alerts {
    private Alerts() {
    }

    /**
     * 提示框
     * @param message 提示内容
     */
    public static void tips(String message) {
        if (ObjectUtils.isEmpty(message)) {
            return;
        }

        new MaterialDialog.Builder(ActivityLifcycleManager.get().current())
                .content(message)
                .positiveText("确定")
                .show();
    }

    /**
     * 消息弹窗，必须输入非空字符串
     * @param title 标题
     * @param message 描述文本
     * @param hint 输入提示
     */
    public static Observable<String> prompt(CharSequence title, CharSequence message, CharSequence hint) {
        return Observable.create(emitter -> {
            MaterialDialog materialDialog = new MaterialDialog.Builder(ActivityLifcycleManager.get().current())
                    .title(title)
                    .content(message)
                    .inputType(InputType.TYPE_CLASS_TEXT)
                    .input(hint, "", false, (dialog, input) -> {
                        dialog.dismiss();

                        String value = input.toString().trim();
                        if (ObjectUtils.isEmpty(value)) {
                            emitter.onError(new ArgumentException("填写内容不能为空"));
                        } else {
                            emitter.onNext(input.toString().trim());
                            emitter.onComplete();
                        }
                    })
                    .cancelListener(dialog -> emitter.onError(new ArgumentException("已取消填写")))
                    .show();

            emitter.setCancellable(() -> {
                if (materialDialog.isShowing()) {
                    materialDialog.dismiss();
                }
            });
        });
    }

    /**
     * 列表弹窗
     * @param title 标题
     * @param items 可选项
     */
    public static Observable<CharSequence> list(CharSequence title, CharSequence... items) {
        return Observable.create(emitter -> {
            MaterialDialog materialDialog = new MaterialDialog.Builder(ActivityLifcycleManager.get().current())
                    .title(title)
                    .items(items)
                    .itemsCallback((dialog, itemView, position, text) -> {
                        emitter.onNext(text);
                        emitter.onComplete();
                    })
                    .cancelListener(dialog -> {
                        emitter.onError(new ArgumentException("已取消"));
                    })
                    .show();

            emitter.setCancellable(() -> {
                if (materialDialog.isShowing()) {
                    materialDialog.dismiss();
                }
            });
        });
    }

    /**
     * 列表弹窗
     * @param anchor 锚
     * @param items 可选项
     */
    public static Observable<Integer> listOnAnchor(@NonNull View anchor, CharSequence... items) {
        return listOnAnchor(anchor, -1, items);
    }
    /**
     * 列表弹窗
     * @param anchor 锚
     * @param selected 当前选中项
     * @param items 可选项
     */
    public static Observable<Integer> listOnAnchor(@NonNull View anchor, int selected, CharSequence... items) {
        return Observable.create(emitter -> {
            ListPopup popup = new ListPopup(AppUtils.getContext())
                    .items(items)
                    .itemsCallback(data -> {
                        int index = -1;
                        for (int i = 0; i < items.length; i++) {
                            if (data.equals(items[i])) {
                                index = i;
                                break;
                            }
                        }

                        if (index >= 0) {
                            emitter.onNext(index);
                            emitter.onComplete();
                        } else {
                            emitter.onError(new ArgumentException("无效的选中项"));
                        }
                    })
                    .cancelListener(() -> {
                        emitter.onError(new ArgumentException("已取消"));
                    });
            popup.setWidth(DisplayInfo.dp2px(140));
            popup.setSelected(selected);
            popup.showOnAnchor(anchor,
                    RelativePopupWindow.VerticalPosition.ALIGN_TOP, RelativePopupWindow.HorizontalPosition.ALIGN_LEFT,
                    -DisplayInfo.dp2px(4), DisplayInfo.dp2px(12));

            emitter.setCancellable(() -> {
                if (popup.isShowing()) {
                    popup.close();
                }
            });
        });
    }

}
