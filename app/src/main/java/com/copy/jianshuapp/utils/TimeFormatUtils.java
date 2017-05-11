package com.copy.jianshuapp.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间格式化相关工具类
 * @version imkarl 2017-05
 */
public class TimeFormatUtils {
    private TimeFormatUtils() {
    }


    private static int CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR);
    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z", Locale.getDefault());

    public static String formatCurrentTime() {
        return SIMPLE_DATE_FORMAT.format(new Date());
    }

    public static String format(long time, String dstPatten) {
        Date date = new Date(formatTime(time));
        if (dstPatten == null || dstPatten.trim().equals("")) {
            dstPatten = format(date);
        }
        return new SimpleDateFormat(dstPatten, Locale.getDefault()).format(date);
    }

    public static String formatDuration(long time, String dstFormate) {
        time = formatTime(time);
        Date date = new Date(time);
        if (dstFormate == null || dstFormate.trim().equals("")) {
            dstFormate = format(date);
        }
        return formatDuration((System.currentTimeMillis() - time) / 1000, dstFormate, date);
    }

    private static String formatDuration(long dis, String dstFormate, Date date) {
        if (dis < 60) {
            return "刚刚";
        }
        if (dis < 3600) {
            return (dis / 60) + "分钟前";
        }
        if (dis < 86400) {
            return (dis / 3600) + "小时前";
        }
        return new SimpleDateFormat(dstFormate, Locale.getDefault()).format(date);
    }

    private static String format(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.YEAR) == CURRENT_YEAR) {
            return "MM-dd HH:mm";
        }
        return "yy-MM-dd HH:mm";
    }

    private static long formatTime(long time) {
        if (String.valueOf(time).length() == 10) {
            return time * 1000;
        }
        return time;
    }

    public static boolean isSameYear(long timeInSeconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(1000 * timeInSeconds);
        return CURRENT_YEAR == calendar.get(Calendar.YEAR);
    }

    public static String formatDuration(long length) {
        if (length < 60) {
            return length + "秒";
        }
        if (length < 3600) {
            return (length / 60) + "分钟";
        }
        if (length < 86400) {
            return (length / 3600) + "小时";
        }
        return (length / 86400) + "天";
    }
}
