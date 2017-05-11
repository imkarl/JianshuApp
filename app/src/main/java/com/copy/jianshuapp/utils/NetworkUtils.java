package com.copy.jianshuapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.copy.jianshuapp.common.AppUtils;

/**
 * 网络相关工具类
 * @version imkarl 2017-04
 */
public class NetworkUtils {

    /**
     * 判断网络连接是否有效（此时可传输数据）
     * @return boolean 不管wifi，还是mobile，只有当前在连接状态（可有效传输数据）才返回true,反之false
     */
    public static boolean isConnected() {
        NetworkInfo net = ((ConnectivityManager) AppUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return net != null && net.isConnected();
    }
    /**
     * 判断有无网络正在连接中（查找网络、校验、获取IP等）
     * @return boolean 不管wifi，还是mobile，只有当前在连接状态（可有效传输数据）才返回true,反之false
     */
    public static boolean isConnectedOrConnecting(Context context) {
        NetworkInfo[] nets = ((ConnectivityManager) AppUtils.getContext().getSystemService(Context.CONNECTIVITY_SERVICE)).getAllNetworkInfo();
        if (nets != null) {
            for (NetworkInfo net : nets) {
                if (net.isConnectedOrConnecting()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * GPRS    2G(2.5) General Packet Radia Service 114kbps
     * EDGE    2G(2.75G) Enhanced Data Rate for GSM Evolution 384kbps
     * UMTS    3G WCDMA 联通3G Universal MOBILE Telecommunication System 完整的3G移动通信技术标准
     * CDMA    2G 电信 Code Division Multiple Access 码分多址
     * EVDO_0  3G (EVDO 全程 CDMA2000 1xEV-DO) Evolution - Data Only (Data Optimized) 153.6kps - 2.4mbps 属于3G
     * EVDO_A  3G 1.8mbps - 3.1mbps 属于3G过渡，3.5G
     * 1xRTT   2G CDMA2000 1xRTT (RTT - 无线电传输技术) 144kbps 2G的过渡,
     * HSDPA   3.5G 高速下行分组接入 3.5G WCDMA High Speed Downlink Packet Access 14.4mbps
     * HSUPA   3.5G High Speed Uplink Packet Access 高速上行链路分组接入 1.4 - 5.8 mbps
     * HSPA    3G (分HSDPA,HSUPA) High Speed Packet Access
     * IDEN    2G Integrated Dispatch Enhanced Networks 集成数字增强型网络 （属于2G，来自维基百科）
     * EVDO_B  3G EV-DO Rev.B 14.7Mbps 下行 3.5G
     * LTE     4G Long Term Evolution FDD-LTE 和 TDD-LTE , 3G过渡，升级版 LTE Advanced 才是4G
     * EHRPD   3G CDMA2000向LTE 4G的中间产物 Evolved High Rate Packet Data HRPD的升级
     * HSPAP   3G HSPAP 比 HSDPA 快些
     *
     * @return {@link  NetworkType}
     */
    public static NetworkType getNetworkType() {
        Context context = AppUtils.getContext();
        int type = -1;
        NetworkInfo networkInfo = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (networkInfo != null) {
            type = networkInfo.getType();
        }

        switch (type) {
            case ConnectivityManager.TYPE_WIFI:
                return NetworkType.WIFI;
            case ConnectivityManager.TYPE_MOBILE:
            case ConnectivityManager.TYPE_MOBILE_DUN:
            case ConnectivityManager.TYPE_MOBILE_HIPRI:
            case ConnectivityManager.TYPE_MOBILE_MMS:
            case ConnectivityManager.TYPE_MOBILE_SUPL:
                int teleType = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getNetworkType();
                switch (teleType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        return NetworkType.MOBILE_2G;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        return NetworkType.MOBILE_3G;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        return NetworkType.MOBILE_4G;
                    default:
                        return NetworkType.MOBILE_UNKNOWN;
                }
            default:
                return NetworkType.UNKNOWN;
        }
    }

    public enum NetworkType {
        UNKNOWN,
        WIFI,
        MOBILE_UNKNOWN,
        MOBILE_2G,
        MOBILE_3G,
        MOBILE_4G;

        public boolean isMobile() {
            boolean isMobile = false;
            switch (this) {
                case MOBILE_UNKNOWN:
                case MOBILE_2G:
                case MOBILE_3G:
                case MOBILE_4G:
                    isMobile = true;
                    break;
            }
            return isMobile;
        }

        public boolean isWifi() {
            return this == WIFI;
        }

    }

}
