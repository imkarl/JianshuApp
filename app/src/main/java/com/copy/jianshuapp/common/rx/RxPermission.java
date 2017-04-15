package com.copy.jianshuapp.common.rx;

import android.Manifest;

import com.copy.jianshuapp.common.ActivityLifcycleManager;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * 动态权限
 * @version imkarl 2016-08
 *
 * @tips
 *
 * <pre>
 *     // 请求全部授权
 *     RxPermission
 *       .request(Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE)
 *       .subscribe(granted -> { // true:全部通过，false:至少有一个权限未通过
 *           if (granted) {
 *               // 表示已全部授权
 *           }
 *       });
 * </pre>
 *
 * <pre>
 *     // 依次请求授权
 *     RxPermission
 *       .requestEach(Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE)
 *       .subscribe(permission -> { // 会响应2个结果
 *           if (permission.granted) {
 *               // `permission.name`表示已授权的权限名称
 *           }
 *       });
 * </pre>
 *
 * <pre>
 *     // 依次请求授权
 *     RxPermission
 *       .requestList(Manifest.permission.CAMERA, Manifest.permission.READ_PHONE_STATE)
 *       .subscribe(permissions -> { // permissions包含所有结果
 *           if (permissions.size()) {
 *               // `permission.name`表示已授权的权限名称
 *           }
 *       });
 * </pre>
 */
public final class RxPermission {
    private RxPermission() {}

    private static RxPermissions permission() {
        return new RxPermissions(ActivityLifcycleManager.get().current());
    }

    /**
     * 请求获取权限
     * @param permissions 需要获取的权限
     * @return true:全部通过，false:至少有一个权限未通过
     * @see Manifest.permission
     * @see Manifest.permission_group
     */
    public static Observable<Boolean> request(String... permissions) {
        return permission().request(permissions);
    }

    /**
     * 请求获取权限
     * @param permissions 需要获取的权限
     * @return true:全部通过，false:至少有一个权限未通过
     * @see Manifest.permission
     * @see Manifest.permission_group
     */
    public static Observable<Permission> requestEach(String... permissions) {
        return permission().requestEach(permissions)
                .map(permission -> new Permission(permission.name, permission.granted));
    }

    /**
     * 请求获取权限
     * @param permissions 需要获取的权限
     * @return true:全部通过，false:至少有一个权限未通过
     * @see Manifest.permission
     * @see Manifest.permission_group
     */
    public static Single<List<Permission>> requestList(String... permissions) {
        return requestEach(permissions).toList();
    }



    public static class Permission {
        private final String name;
        private final boolean granted;

        public Permission(String name, boolean granted) {
            this.name = name;
            this.granted = granted;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Permission that = (Permission) o;

            if (granted != that.granted) return false;
            return name.equals(that.name);
        }

        @Override
        public int hashCode() {
            int result = name.hashCode();
            result = 31 * result + (granted ? 1 : 0);
            return result;
        }

        @Override
        public String toString() {
            return "Permission{" +
                    "name='" + name + '\'' +
                    ", granted=" + granted +
                    '}';
        }

        public String getName() {
            return name;
        }
        public boolean isGranted() {
            return granted;
        }
    }

}
