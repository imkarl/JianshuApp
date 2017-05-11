package com.copy.jianshuapp.common;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.copy.jianshuapp.modellayer.local.SettingsUtils;

import java.util.UUID;

/**
 * 设备基本信息
 * @version imkarl 2017-04
 */
public class DeviceInfo {

    private DeviceInfo() {
    }

    private static final String model = Build.MODEL;
    private static final String brand = Build.BRAND;
    private static final int sdkVersion = Build.VERSION.SDK_INT;
    private static final String cpu = Build.CPU_ABI;
    private static final String cpu2 = Build.CPU_ABI2;

    private static String uniqueId;

    /**
     * 获取设备唯一ID
     * @return 根据设备相关信息生成
     */
    public static String getUniqueId() {
        if (TextUtils.isEmpty(uniqueId)) {
            synchronized (DeviceInfo.class) {
                if (TextUtils.isEmpty(uniqueId)) {
                    String uuid = SettingsUtils.getString(SettingsUtils.UNIQUE_ID);
                    if (TextUtils.isEmpty(uuid)) {
                        uuid = getDeviceId();
                    }
                    if (TextUtils.isEmpty(uuid)) {
                        uuid = getAndroidId();
                    }
                    if (TextUtils.isEmpty(uuid)) {
                        uuid = getSimSerialNumber();
                    }
                    if (TextUtils.isEmpty(uuid)) {
                        uuid = UUID.randomUUID().toString();
                    }
                    uniqueId = uuid;

                    if (!TextUtils.isEmpty(uuid)) {
                        SettingsUtils.put(SettingsUtils.UNIQUE_ID, uuid);
                    }
                }
            }
        }
        return uniqueId;
    }


    public static String getModel() {
        return model;
    }

    public static String getBrand() {
        return brand;
    }

    public static int getSdkVersion() {
        return sdkVersion;
    }

    public static String getCpu() {
        if (!TextUtils.isEmpty(cpu)) {
            return cpu;
        }
        if (!TextUtils.isEmpty(cpu2)) {
            return cpu2;
        }
        return "";
    }


    /**
     * 需要权限: {@link android.Manifest.permission#READ_PHONE_STATE READ_PHONE_STATE}
     * @return 获取失败返回空字符串""
     */
    private static String getSubscriberId() {
        try {
            String subscriberId = ((TelephonyManager) AppUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE)).getSubscriberId();
            if (!TextUtils.isEmpty(subscriberId)) {
                return subscriberId;
            }
        } catch (Exception ignored) {
        }
        return "";
    }
    /**
     * 需要权限: {@link android.Manifest.permission#READ_PHONE_STATE READ_PHONE_STATE}
     * @return 获取失败返回空字符串""
     */
    private static String getDeviceId() {
        try {
            String deviceId = ((TelephonyManager) AppUtils.getContext().getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
            if (Long.parseLong(deviceId) > 0) {
                return deviceId;
            }
        } catch (Exception ignored) {
        }
        return "";
    }
    /**
     * 需要权限: {@link android.Manifest.permission#READ_PHONE_STATE READ_PHONE_STATE}
     * @return 获取失败返回空字符串""
     */
    private static String getSimSerialNumber() {
        String tmSerial = ((TelephonyManager) AppUtils.getContext().getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE)).getSimSerialNumber();
        if (TextUtils.isEmpty(tmSerial)) {
            return "";
        }
        return tmSerial;
    }
    /**
     * @return 获取失败返回空字符串""
     */
    private static String getAndroidId() {
        try {
            String androidId = Settings.Secure.getString(AppUtils.getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            if (!"9774d56d682e549c".equals(androidId)) {
                return androidId;
            }
        } catch (SecurityException ignored) {
        }
        return "";
    }


    public static String getDescription() {
        return "DeviceInfo{" +
                "model=" + model +
                ", brand=" + brand +
                ", sdkVersion=" + sdkVersion +
                ", cpu=" + cpu +
                ", cpu2=" + cpu2 +
                ", uniqueId=" + getUniqueId() +
                '}';
    }

}
