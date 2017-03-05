package com.copy.jianshuapp.common.rom;

import android.os.Build;
import android.os.Environment;

import com.copy.jianshuapp.common.CloseUtils;
import com.copy.jianshuapp.common.LogUtils;
import com.copy.jianshuapp.common.ObjectUtils;

import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Properties;

/**
 * ROM 类型
 * @version imkarl 2017-03
 */
public enum RomType {
    UNKNOWN {
        @Override
        boolean isMe() {
            return false;
        }
    },

    MIUI {
//        "ro.miui.ui.version.code",
//        "ro.miui.ui.version.name",
//        "ro.miui.internal.storage",
//        "ro.miui.has_handy_mode_sf",
//        "ro.miui.has_real_blur"
        @Override
        boolean isMe() {
            return containsPropertyKey("ro.miui.");
        }

        public MiuiVersion getVersion() {
            String miuiVersion = getProperty("ro.miui.ui.version.name");
            if (!ObjectUtils.isEmpty(miuiVersion)) {
                if (miuiVersion.toLowerCase().contains("v5")) {
                    return MiuiVersion.V5;
                } else if (miuiVersion.toLowerCase().contains("v6")) {
                    return MiuiVersion.V6;
                } else if (miuiVersion.toLowerCase().contains("v7")) {
                    return MiuiVersion.V7;
                } else if (miuiVersion.toLowerCase().contains("v8")) {
                    return MiuiVersion.V8;
                }
            }
            return MiuiVersion.UNKNOWN;
        }
    },
    Flyme {
//        "persist.sys.use.flyme.icon", "ro.flyme.published", "ro.meizu.setupwizard.flyme"
//        "ro.build.user", "ro.build.display.id"  =>  "flyme"
        @Override
        boolean isMe() {
            return Build.DISPLAY.toLowerCase().contains("flyme")
                    || containsPropertyKey(".flyme")
                    || containsPropertyValue("flyme");
        }
    },
    EMUI {
//        "ro.build.hw_emui_api_level", "ro.build.version.emui"
        @Override
        boolean isMe() {
            return containsPropertyKey("emui");
        }
    },
    ;

    abstract boolean isMe();

    /**
     * 获取当前ROM类型
     */
    public static RomType current() {
        for (RomType type : RomType.values()) {
            if (type.isMe()) {
                return type;
            }
        }
        return RomType.UNKNOWN;
    }



    /**
     * 是否存在该key值
     * @return 存在任意一个属性，即返回true
     */
    private static boolean containsPropertyKey(String... keys) {
        if (ObjectUtils.isEmpty(keys)) {
            return false;
        }

        boolean flag = false;
        for (String key : keys) {
            if (getKeys().contains(key)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
    /**
     * 是否存在该value值
     * @return 包含目标内容，则返回true
     */
    private static boolean containsPropertyValue(String... values) {
        if (ObjectUtils.isEmpty(values)) {
            return false;
        }

        boolean flag = false;
        for (String value : values) {
            if (getValues().contains(value)) {
                flag = true;
                break;
            }
        }
        return flag;
    }
    /**
     * 根据键值获取属性值
     * @return 不存在则返回null
     */
    private static String getProperty(String key) {
        if (ObjectUtils.isEmpty(key)) {
            return null;
        }

        return getProperties().getProperty(key, null);
    }

    private static Properties sProperties = null;
    private static Properties getProperties() {
        if (sProperties == null) {
            synchronized (RomType.class) {
                if (sProperties == null) {
                    File file = new File(Environment.getRootDirectory(), "build.prop");
                    if (file.exists()) {
                        FileInputStream fis = null;
                        try {
                            sProperties = new Properties();
                            fis = new FileInputStream(file);
                            sProperties.load(fis);
                        } catch (Exception e) {
                            LogUtils.w(e);
                        } finally {
                            CloseUtils.closeIO(fis);
                        }
                    }
                }
            }
        }
        return sProperties;
    }


    private static String sKeyLink = null;
    private static String sValueLink = null;
    private static void getKeyValues() {
        if (sKeyLink == null) {
            synchronized (RomType.class) {
                if (sKeyLink == null) {
                    sKeyLink = Arrays.toString(getProperties().keySet().toArray()).toLowerCase();
                    sValueLink = Arrays.toString(getProperties().values().toArray());
                }
            }
        }
    }

    private static String getKeys() {
        getKeyValues();
        return sKeyLink;
    }
    private static String getValues() {
        getKeyValues();
        return sValueLink;
    }

}
