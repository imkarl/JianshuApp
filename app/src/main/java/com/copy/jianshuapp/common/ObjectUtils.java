package com.copy.jianshuapp.common;

import android.os.Bundle;
import android.text.TextUtils;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Java对象的常用处理
 * @version imkarl 2016-08
 * @version imkarl 2017-04 修正isEmpty()的错误
 */
public final class ObjectUtils {
    private ObjectUtils() {}

    public static boolean isEmpty(Object obj) {
        if (obj instanceof CharSequence) {
            return isEmpty((CharSequence)obj);
        } else if (obj instanceof Bundle) {
            return isEmpty((Bundle)obj);
        } else if (obj instanceof Collection) {
            return isEmpty((Collection)obj);
        } else if (obj instanceof Map) {
            return isEmpty((Map)obj);
        }
        return obj == null;
    }

    public static boolean isEmpty(CharSequence str) {
        return TextUtils.isEmpty(str) || Pattern.matches("\\s", str);
    }

    public static boolean isEmpty(Bundle bundle) {
        return bundle == null || bundle.isEmpty();
    }

    public static boolean isEmpty(Collection list) {
        return list == null || list.isEmpty();
    }

    public static <T> boolean isEmpty(T[] list) {
        return list == null || list.length==0;
    }

    public static boolean isEmpty(Map map) {
        return map == null || map.isEmpty();
    }

    public static int toInt(CharSequence value) {
        if (isEmpty(value)) {
            return -1;
        }
        try {
            return Integer.parseInt(value.toString().trim());
        } catch (Exception e) {
            return -1;
        }
    }
    public static long toLong(CharSequence value) {
        if (isEmpty(value)) {
            return -1;
        }
        try {
            return Long.parseLong(value.toString().trim());
        } catch (Exception e) {
            return -1;
        }
    }
    public static double toDobule(CharSequence value) {
        if (isEmpty(value)) {
            return -1;
        }
        try {
            return Double.parseDouble(value.toString().trim());
        } catch (Exception e) {
            return -1;
        }
    }

    public static boolean equals(Object a, Object b) {
        return (a == b) || (a != null && a.equals(b));
    }
    public static boolean equals(File src, File target) {
        return FileUtils.equals(src, target);
    }

}
