package com.copy.jianshuapp.uilayer.base;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.annotation.IdRes;

/**
 * FragmentManager辅助类
 * @version imkarl 2017-03
 */
public class FragmentManagerHelper {

    private final Object mComponent;

    public FragmentManagerHelper(Fragment fragment) {
        this.mComponent = fragment;
    }
    public FragmentManagerHelper(Activity activity) {
        this.mComponent = activity;
    }

    public FragmentManager getFragmentManager() {
        if (mComponent == null) {
            return null;
        }
        if (mComponent instanceof Fragment) {
            return ((Fragment) mComponent).getFragmentManager();
        } else if (mComponent instanceof Activity) {
            return ((Activity) mComponent).getFragmentManager();
        }
        return null;
    }

    public FragmentTransactionHelper beginTransaction() {
        return new FragmentTransactionHelper(this);
    }


    public static boolean isAdded(Fragment fragment) {
        // FIXME
        return fragment.isAdded() || fragment.getTag() != null || fragment.getId() != 0;
    }


    public static String createTag(Fragment fragment) {
        return createTag(fragment.getClass());
    }
    public static String createTag(Class<? extends Fragment> clazz) {
        return clazz.getName();
    }


    public <T extends Fragment> T find(@IdRes int id) {
        FragmentManager fm = getFragmentManager();
        return (T) fm.findFragmentById(id);
    }
    public <T extends Fragment> T find(String tag) {
        FragmentManager fm = getFragmentManager();
        return (T) fm.findFragmentByTag(tag);
    }
    public <T extends Fragment> T find(Class<T> fragmentClass) {
        return find(createTag(fragmentClass));
    }
    public <T extends Fragment> T findOrCreate(Class<T> fragmentClass) {
        return findOrCreate(fragmentClass, createTag(fragmentClass));
    }
    public <T extends Fragment> T findOrCreate(Class<T> fragmentClass, String tag) {
        T fragment = find(tag);
        if (fragment == null) {
            try {
                fragment = fragmentClass.newInstance();
            } catch (Exception ignored) { }
        }
        return fragment;
    }

}
