package com.copy.jianshuapp.uilayer.widget.dialog;

import android.text.InputType;

import com.afollestad.materialdialogs.MaterialDialog;
import com.copy.jianshuapp.common.ActivityLifcycleManager;
import com.copy.jianshuapp.common.ObjectUtils;
import com.copy.jianshuapp.exception.ArgumentException;

import io.reactivex.Observable;

/**
 * 弹窗相关
 * @version imkarl 2017-04
 */
public class Alerts {
    private Alerts() {
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

}
