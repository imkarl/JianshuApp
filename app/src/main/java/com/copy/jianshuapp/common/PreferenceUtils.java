package com.copy.jianshuapp.common;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.reflect.TypeToken;

/**
 * SharedPreferences工具类
 * @version imkarl 2017-03
 */
public class PreferenceUtils {

    private SharedPreferences mSharedPreferences;

    public PreferenceUtils(Context context, String name) {
        mSharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
    }


    public boolean getBoolean(String key) {
        return getBoolean(key, false);
    }
    public boolean getBoolean(String key, boolean defValue) {
        return mSharedPreferences.getBoolean(key, defValue);
    }

    public String getString(String key) {
        return getString(key, "");
    }
    public String getString(String key, String defValue) {
        return mSharedPreferences.getString(key, defValue);
    }

    public float getFloat(String key) {
        return getFloat(key, 0F);
    }
    public float getFloat(String key, float defValue) {
        return mSharedPreferences.getFloat(key, defValue);
    }

    public int getInt(String key) {
        return getInt(key, 0);
    }
    public int getInt(String key, int defValue) {
        return mSharedPreferences.getInt(key, defValue);
    }

    public long getLong(String key) {
        return getLong(key, 0);
    }
    public long getLong(String key, long defValue) {
        return mSharedPreferences.getLong(key, defValue);
    }


    /**
     * 保存数据
     * @return 是否保存成功
     */
    public boolean put(String key, Object value) {
        if (ObjectUtils.isEmpty(key)) {
            return false;
        }
        if (value == null) {
            return remove(key);
        }

        SharedPreferences.Editor edit = mSharedPreferences.edit();
        boolean flag = false;
        if (value instanceof String) {
            flag = edit.putString(key, (String) value).commit();
        } else if (value instanceof Boolean) {
            flag = edit.putBoolean(key, (Boolean) value).commit();
        } else if (value instanceof Float) {
            flag = edit.putFloat(key, (Float) value).commit();
        } else if (value instanceof Integer) {
            flag = edit.putInt(key, (Integer) value).commit();
        } else if (value instanceof Long) {
            flag = edit.putLong(key, (Long) value).commit();
        }
        return flag;
    }


    /**
     * 删除数据
     * @return 是否删除成功
     */
    public boolean remove(String key) {
        return mSharedPreferences.edit().remove(key).commit();
    }

}
