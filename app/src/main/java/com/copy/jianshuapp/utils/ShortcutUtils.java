package com.copy.jianshuapp.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.copy.jianshuapp.R;
import com.copy.jianshuapp.common.AppUtils;
import com.copy.jianshuapp.modellayer.enums.SubscriptionType;
import com.copy.jianshuapp.uilayer.subject.CollectionActivity;
import com.copy.jianshuapp.uilayer.user.UserPushingDetailActivity;

/**
 * 快捷方式相关工具类
 * @version imkarl 2017-04
 */
public class ShortcutUtils {

    private static final String ACTION_ADD_SHORTCUT = "com.android.launcher.action.INSTALL_SHORTCUT";

    public static void addShortcut(SubscriptionType type, String id, String name, Bitmap icon) {
        Intent shortcutIntent = new Intent();
        Bundle bundle = new Bundle();
        switch (type) {
            case user:
                shortcutIntent.setClass(AppUtils.getContext(), UserPushingDetailActivity.class);
                bundle.putString(Constants.Extras.USER_ID, id);
                break;
            case collection:
                shortcutIntent.setClass(AppUtils.getContext(), CollectionActivity.class);
                bundle.putString(Constants.Extras.COLLECTION_ID, id);
                break;
        }
        bundle.putBoolean(Constants.Extras.IS_FROM_SHORTCUT, true);
        shortcutIntent.putExtras(bundle);
        addShortcut(shortcutIntent, name, icon, -1);
    }

    private static void addShortcut(Intent shortcutIntent, String name, Bitmap icon, int iconResId) {
        Context context = AppUtils.getContext();
        Intent addIntent = new Intent();
        addIntent.putExtra("duplicate", false);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
        addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, name);
        if (icon != null) {
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON, icon);
        } else {
            addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, Intent.ShortcutIconResource.fromContext(context, iconResId));
        }
        addIntent.setAction(ACTION_ADD_SHORTCUT);
        context.sendBroadcast(addIntent);

        ToastUtils.show(R.string.has_added);
    }

}
