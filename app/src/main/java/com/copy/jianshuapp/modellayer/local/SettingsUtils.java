package com.copy.jianshuapp.modellayer.local;

import com.copy.jianshuapp.common.ObjectUtils;
import com.copy.jianshuapp.common.PreferenceUtils;
import com.copy.jianshuapp.utils.Constants;

/**
 * 设置相关工具类
 * @version imkarl 2017-03
 */
public class SettingsUtils {
    private SettingsUtils() { }

    private final static String PREFERNCE_NAME = "settings";
    private static PreferenceUtils sPreference;

    private static PreferenceUtils getPreference() {
        if (sPreference == null) {
            synchronized (SettingsUtils.class) {
                if (sPreference == null) {
                    sPreference = new PreferenceUtils(PREFERNCE_NAME);
                }
            }
        }
        return sPreference;
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }
    public static boolean getBoolean(String key, boolean defValue) {
        return getPreference().getBoolean(key, defValue);
    }

    public static String getString(String key) {
        return getString(key, "");
    }
    public static String getString(String key, String defValue) {
        return getPreference().getString(key, defValue);
    }

    public static float getFloat(String key) {
        return getFloat(key, 0F);
    }
    public static float getFloat(String key, float defValue) {
        return getPreference().getFloat(key, defValue);
    }

    public static int getInt(String key) {
        return getInt(key, 0);
    }
    public static int getInt(String key, int defValue) {
        return getPreference().getInt(key, defValue);
    }

    public static long getLong(String key) {
        return getLong(key, 0);
    }
    public static long getLong(String key, long defValue) {
        return getPreference().getLong(key, defValue);
    }

    public static boolean put(String key, Object value) {
        return getPreference().put(key, value);
    }

    public static boolean remove(String key) {
        return getPreference().remove(key);
    }


    public static final String HAS_SHOW_GUIDE = "has_show_guide";

    public static final String HAS_LOGIN = "has_login";
    public static final String HAS_REGIST = "has_regist";

    public static final String GUID = "guid";

    private static final String THEME = "theme";
    public static Constants.JSTheme getTheme() {
        String theme = getString(THEME);
        if (ObjectUtils.isEmpty(theme)) {
            return Constants.JSTheme.DAY;
        }
        return Constants.JSTheme.valueOf(theme);
    }

}
