package com.copy.jianshuapp.uilayer.base;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.annotation.IdRes;
import android.text.TextUtils;

/**
 * FragmentTransaction辅助类
 * @version imkarl 2017-03
 */
public class FragmentTransactionHelper {

    private final FragmentManagerHelper mFragmentManager;
    private final FragmentTransaction mTransaction;

    public FragmentTransactionHelper(FragmentManagerHelper manager) {
        this.mFragmentManager = manager;
        this.mTransaction = this.mFragmentManager.getFragmentManager().beginTransaction();
    }
    public FragmentTransactionHelper(FragmentManagerHelper manager, FragmentTransaction transaction) {
        this.mFragmentManager = manager;
        this.mTransaction = transaction;
    }

    public FragmentTransactionHelper replace(@IdRes int id, Class<? extends Fragment> fragmentClass) {
        return replace(id, mFragmentManager.findOrCreate(fragmentClass));
    }
    public FragmentTransactionHelper replace(@IdRes int id, Fragment fragment) {
        Fragment oldFragment = mFragmentManager.find(id);
        if (oldFragment != null) {
            if (oldFragment != fragment) {
                mTransaction.remove(oldFragment);
                mTransaction.replace(id, fragment);
            }
        } else {
            mTransaction.replace(id, fragment);
        }
        return this;
    }


    public FragmentTransactionHelper add(@IdRes int id, Class<? extends Fragment> fragmentClass) {
        return add(id, mFragmentManager.findOrCreate(fragmentClass));
    }
    public FragmentTransactionHelper add(@IdRes int id, Fragment fragment) {
        add(id, fragment, FragmentManagerHelper.createTag(fragment));
        return this;
    }
    public FragmentTransactionHelper add(@IdRes int id, Fragment fragment, String tag) {
        if (!FragmentManagerHelper.isAdded(fragment)) {
            if (TextUtils.isEmpty(tag)) {
                mTransaction.add(id, fragment);
            } else {
                Fragment oldFragment = mFragmentManager.find(tag);
                if (oldFragment != null) {
                    if (oldFragment != fragment) {
                        mTransaction.add(id, fragment, tag);
                    }
                } else {
                    mTransaction.add(id, fragment, tag);
                }
            }
        } else {
            mTransaction.show(fragment);
        }
        return this;
    }


    public FragmentTransactionHelper remove(Fragment fragment) {
        mTransaction.remove(fragment);
        return this;
    }


    public FragmentTransactionHelper hide(Fragment fragment) {
        if (fragment == null) {
            return this;
        }
        if (FragmentManagerHelper.isAdded(fragment)) {
            mTransaction.hide(fragment);
        }
        return this;
    }
    public FragmentTransactionHelper hide(Class<? extends Fragment> fragmentClass) {
        hide(mFragmentManager.find(fragmentClass));
        return this;
    }


    public FragmentTransactionHelper show(Fragment fragment) {
        mTransaction.show(fragment);
        return this;
    }


    public void commit() {
        mTransaction.commit();
    }
    public void commitAllowingStateLoss() {
        mTransaction.commitAllowingStateLoss();
    }

}
