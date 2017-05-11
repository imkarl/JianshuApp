package com.copy.jianshuapp.common;

import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcelable;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;

import com.copy.jianshuapp.utils.pair.KeyValuePair;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Bundle相关工具类
 * @version imkarl 2017-04
 */
public final class BundleUtils {
    private BundleUtils() {}

    public static Bundle toBundle(KeyValuePair<String, ?>... params) {
        Bundle bundle = new Bundle();
        if (params != null && params.length > 0) {
            for (KeyValuePair<String, ?> pair : params) {
                Object value = pair.getValue();

                if (value instanceof IBinder) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                        bundle.putBinder(pair.getKey(), (IBinder) value);
                    }
                } else if (value instanceof Bundle) {
                    bundle.putBundle(pair.getKey(), (Bundle) value);
                } else if (value instanceof Boolean) {
                    bundle.putBoolean(pair.getKey(), (boolean) value);
                } else if (value instanceof boolean[]) {
                    bundle.putBooleanArray(pair.getKey(), (boolean[]) value);
                } else if (value instanceof Double) {
                    bundle.putDouble(pair.getKey(), (double) value);
                } else if (value instanceof double[]) {
                    bundle.putDoubleArray(pair.getKey(), (double[]) value);
                } else if (value instanceof Long) {
                    bundle.putLong(pair.getKey(), (long) value);
                } else if (value instanceof long[]) {
                    bundle.putLongArray(pair.getKey(), (long[]) value);
                } else if (value instanceof Byte) {
                    bundle.putByte(pair.getKey(), (byte) value);
                } else if (value instanceof byte[]) {
                    bundle.putByteArray(pair.getKey(), (byte[]) value);
                } else if (value instanceof Character) {
                    bundle.putChar(pair.getKey(), (char) value);
                } else if (value instanceof char[]) {
                    bundle.putCharArray(pair.getKey(), (char[]) value);
                } else if (value instanceof String) {
                    bundle.putString(pair.getKey(), (String) value);
                } else if (value instanceof String[]) {
                    bundle.putStringArray(pair.getKey(), (String[]) value);
                } else if (value instanceof CharSequence) {
                    bundle.putCharSequence(pair.getKey(), (CharSequence) value);
                } else if (value instanceof CharSequence[]) {
                    bundle.putCharSequenceArray(pair.getKey(), (CharSequence[]) value);
                } else if (value instanceof Float) {
                    bundle.putFloat(pair.getKey(), (float) value);
                } else if (value instanceof float[]) {
                    bundle.putFloatArray(pair.getKey(), (float[]) value);
                } else if (value instanceof Integer) {
                    bundle.putInt(pair.getKey(), (int) value);
                } else if (value instanceof int[]) {
                    bundle.putIntArray(pair.getKey(), (int[]) value);
                } else if (value instanceof Parcelable) {
                    bundle.putParcelable(pair.getKey(), (Parcelable) value);
                } else if (value instanceof Parcelable[]) {
                    bundle.putParcelableArray(pair.getKey(), (Parcelable[]) value);
                } else if (value instanceof Short) {
                    bundle.putShort(pair.getKey(), (short) value);
                } else if (value instanceof short[]) {
                    bundle.putShortArray(pair.getKey(), (short[]) value);
                } else if (value instanceof Serializable) {
                    bundle.putSerializable(pair.getKey(), (Serializable) value);
                } else if (value instanceof Size) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        bundle.putSize(pair.getKey(), (Size) value);
                    }
                } else if (value instanceof SizeF) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        bundle.putSizeF(pair.getKey(), (SizeF) value);
                    }
                } else if (value instanceof List) {
                    try {
                        bundle.putIntegerArrayList(pair.getKey(), (ArrayList<Integer>) value);
                    } catch (ClassCastException ignored) {
                    }
                    try {
                        bundle.putStringArrayList(pair.getKey(), (ArrayList<String>) value);
                    } catch (ClassCastException ignored) {
                    }
                    try {
                        bundle.putCharSequenceArrayList(pair.getKey(), (ArrayList<CharSequence>) value);
                    } catch (ClassCastException ignored) {
                    }
                    try {
                        bundle.putParcelableArrayList(pair.getKey(), (ArrayList<? extends Parcelable>) value);
                    } catch (ClassCastException ignored) {
                    }
                } else if (value instanceof SparseArray) {
                    try {
                        bundle.putSparseParcelableArray(pair.getKey(), (SparseArray<? extends Parcelable>) value);
                    } catch (ClassCastException ignored) {
                    }
                }
            }
        }
        return bundle;
    }

}
