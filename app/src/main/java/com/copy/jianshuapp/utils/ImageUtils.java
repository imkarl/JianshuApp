package com.copy.jianshuapp.utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.text.TextUtils;

import com.copy.jianshuapp.JSApi;
import com.copy.jianshuapp.common.AppInfo;
import com.copy.jianshuapp.common.FileUtils;
import com.copy.jianshuapp.common.ObjectUtils;
import com.copy.jianshuapp.exception.ArgumentException;
import com.copy.jianshuapp.exception.NullException;
import com.copy.jianshuapp.third.FrescoManager;
import com.copy.jianshuapp.uilayer.listeners.OnLoadedListener;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

/**
 * 图片相关工具类
 * @version imkarl 2017-04
 */
public class ImageUtils {

    private static final String ANDROID_RESOURCE = "res://";
    private static final String PATH_SEPARATOR = "/";

    /**
     * Uri uri = Uri.parse("res://包名(实际可以是任何字符串甚至留空)/" + R.drawable.ic_launcher);
     */
    public static Uri parseUri(@DrawableRes int resourceId) {
        if (resourceId <= 0) {
            return null;
        }
        return Uri.parse(ANDROID_RESOURCE + AppInfo.getPackageName() + PATH_SEPARATOR + resourceId);
    }

    /**
     * 解析URL
     */
    public static Uri parseUri(String url) {
        if (ObjectUtils.isEmpty(url)) {
            return null;
        }

        if (FileUtils.isLocalPath(url)) {
            // local file
            return parseUri(new File(FileUtils.getAbsolutePath(url)));
        } else {
            // remote url
            return Uri.parse(url);
        }
    }

    public static Uri parseUri(File file) {
        if (file == null) {
            return null;
        }
        return Uri.fromFile(file);
    }



    /**
     * 限定宽最多为<Width>（长自适应）
     */
    public static String format(String originalUrl, int width) {
        return formatByQuality(originalUrl, width, 100);
    }
    /**
     * 限定宽高最多为<Width> x <Height>（有一个边会在缩小时裁剪超出部分，宽高均小于指定大小则不处理）
     */
    public static String format(String originalUrl, int width, int height) {
        return formatByQuality(originalUrl, width, height, 100);
    }

    /**
     * 限定宽最多为<Width>（长自适应）
     */
    public static String formatByQuality(String originalUrl, int width, int quality) {
        return format(originalUrl, 2, width, 0, quality);
    }
    /**
     * 限定宽高最多为<Width> x <Height>（有一个边会在缩小时裁剪超出部分，宽高均小于指定大小则不处理）
     */
    public static String formatByQuality(String originalUrl, int width, int height, int quality) {
        if (TextUtils.isEmpty(originalUrl)) {
            return originalUrl;
        }
        for (String imageHost : JSApi.IMAGE_HOSTS) {
            if (originalUrl.contains(imageHost)) {
                return format(originalUrl, 1, width, height, quality);
            }
        }
        return originalUrl;
    }
    private static String format(String url, int mode, int width, int height, int quality) {
        if (TextUtils.isEmpty(url)) {
            return url;
        }
        if (url.contains("?")) {
            return url;
        }
        StringBuilder result = new StringBuilder(url);
        result.append('?').append("?imageMogr2/auto-orient/strip%7CimageView2/");
        result.append(mode);
        if (width > 0) {
            result.append("/w/").append(width);
        }
        if (height > 0) {
            result.append("/h/").append(height);
        }
        if (quality > 0 && quality < 100) {
            result.append("/q/").append(quality);
        }
        // 转成webp格式、支持渐显、忽略异常
        result.append("/format/webp").append("/interlace/1").append("/ignore-error/1");
        return result.toString();
    }


    /**
     * 加载图片
     * @param uri 图片地址
     */
    public static Observable<Bitmap> loadImage(Uri uri) {
        return loadImage(uri, -1, -1);
    }
    /**
     * 加载图片
     * @param uri 图片地址
     */
    public static Observable<Bitmap> loadImage(Uri uri, int width, int height) {
        if (ObjectUtils.isEmpty(uri)) {
            return Observable.error(new ArgumentException("uri不能为空"));
        }

        return Observable.<Bitmap>create(emitter -> {
            FrescoManager.loadBitmap(uri, width, height, new OnLoadedListener<Bitmap>() {
                @Override
                public void onSuccess(Bitmap bitmap) {
                    if (emitter.isDisposed()) {
                        return;
                    }

                    if (bitmap == null) {
                        emitter.onError(new NullException("读取Bitmap为空"));
                        return;
                    }

                    emitter.onNext(bitmap);
                    emitter.onComplete();
                }
                @Override
                public void onFail() {
                    if (emitter.isDisposed()) {
                        return;
                    }

                    emitter.onError(new NullException("读取Bitmap失败"));
                }
            });
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.io());
    }

}
