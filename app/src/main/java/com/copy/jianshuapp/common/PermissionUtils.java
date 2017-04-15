package com.copy.jianshuapp.common;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

/**
 * 权限相关
 * @version imkarl 2016-08
 */
public class PermissionUtils {
    private PermissionUtils() {}

    public static boolean hasPermission(@NonNull String permission) {
        int perm = AppUtils.getContext().checkCallingOrSelfPermission(permission);
        return perm == PackageManager.PERMISSION_GRANTED;
    }

}
