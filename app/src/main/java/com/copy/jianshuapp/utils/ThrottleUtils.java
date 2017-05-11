package com.copy.jianshuapp.utils;

import android.view.View;

/**
 * 节流相关工具类
 * @version imkarl 2017-04
 */
public class ThrottleUtils {
    private ThrottleUtils() {
    }

    private static final int THROTTLE_TIME = 1000;

    private static ThrottleInfo sLastInfo;
    private static long sLastTime = 0;

    private static class ThrottleInfo {
        final long target;
        final long time;

        ThrottleInfo(long target, long time) {
            this.target = target;
            this.time = time;
        }
    }

    /**
     * 验证是否有效
     * @return 如果超过节流时长限制，即有效事件，则返回true
     */
    public static boolean valid(View view) {
        boolean result = true;
        ThrottleInfo currentInfo = new ThrottleInfo(view.getId(), System.currentTimeMillis());
        if (sLastInfo != null && sLastInfo.target == currentInfo.target) {
            if (currentInfo.time - sLastInfo.time < THROTTLE_TIME) {
                result = false;
            }
        }
        if (result) {
            sLastInfo = currentInfo;
        }
        return result;
    }

    /**
     * 验证是否有效
     * @return 如果超过节流时长限制，即有效事件，则返回true
     */
    public static boolean valid() {
        long current = System.currentTimeMillis();
        boolean result = current - sLastTime > THROTTLE_TIME;
        if (result) {
            sLastTime = current;
        }
        return result;
    }

}
