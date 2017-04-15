package com.copy.jianshuapp.common;

import android.Manifest;
import android.content.Context;
import android.os.Environment;

import java.io.File;

/**
 * 文件管理
 * @version imkarl 2017-04
 */
public class FileManager {

    private FileManager() {
    }

    public static final String DIR_MUSIC = Environment.DIRECTORY_MUSIC; // 音乐
    public static final String DIR_PODCASTS = Environment.DIRECTORY_PODCASTS; // 音频
    public static final String DIR_RINGTONES = Environment.DIRECTORY_RINGTONES; // 铃声
    public static final String DIR_ALARMS = Environment.DIRECTORY_ALARMS; // 闹钟
    public static final String DIR_NOTIFICATIONS = Environment.DIRECTORY_NOTIFICATIONS; // 通知
    public static final String DIR_PICTURES = Environment.DIRECTORY_PICTURES; // 图片
    public static final String DIR_MOVIES = Environment.DIRECTORY_MOVIES; // 电影

    public static final String DIR_DOWNLOADS = Environment.DIRECTORY_DOWNLOADS; // 下载文件
    public static final String DIR_DCIM = Environment.DIRECTORY_DCIM; // 照片

    /**
     * 获取外部存储-根目录
     */
    public static File getExternalRootDir() {
        return Environment.getExternalStorageDirectory();
    }
    /**
     * 获取外部存储-私有存储根目录
     */
    public static File getExternalDir() {
        Context context = AppUtils.getContext();
        File cacheDir = context.getExternalCacheDir();
        if (cacheDir != null) {
            // TODO 更合理的方式
            cacheDir = cacheDir.getParentFile();
        }
        return createDir(cacheDir);
    }
    /**
     * 获取外部存储-私有存储根目录
     * @param child 子目录
     */
    public static File getExternalDir(String child) {
        return createDir(getExternalDir(), child);
    }
    /**
     * 获取外部存储-私有cache文件夹
     */
    public static File getExternalCacheDir() {
        Context context = AppUtils.getContext();
        File cacheDir = context.getExternalCacheDir();
        return createDir(cacheDir);
    }
    /**
     * 获取外部存储-私有cache文件夹
     * @param child 子目录
     */
    public static File getExternalCacheDir(String child) {
        return createDir(getExternalCacheDir(), child);
    }
    /**
     * 获取外部存储-私有files文件夹
     */
    public static File getExternalFilesDir() {
        File cacheDir = getExternalDir();
        if (cacheDir != null) {
            // TODO 更合理的方式
            cacheDir = new File(cacheDir, "files");
        }
        return createDir(cacheDir);
    }
    /**
     * 获取外部存储-私有files文件夹
     * @param child 子目录
     */
    public static File getExternalFilesDir(String child) {
        return createDir(getExternalFilesDir(), child);
    }


    /**
     * 获取APP存储-私有存储根目录
     */
    public static File getDataDir() {
        Context context = AppUtils.getContext();
        File appCacheDir = context.getCacheDir();
        if (appCacheDir != null) {
            // TODO 更合理的方式
            appCacheDir = appCacheDir.getParentFile();
        }
        if(appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/";
            appCacheDir = new File(cacheDirPath);
        }
        appCacheDir.mkdirs();
        return appCacheDir;
    }
    /**
     * 获取APP存储-私有存储根目录
     * @param child 子目录
     */
    public static File getDataDir(String child) {
        return createDir(getDataDir(), child);
    }
    /**
     * 获取APP存储-私有cache文件夹
     */
    public static File getDataCacheDir() {
        Context context = AppUtils.getContext();
        File appCacheDir = context.getCacheDir();
        if(appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/cache/";
            appCacheDir = new File(cacheDirPath);
        }
        return createDir(appCacheDir);
    }
    /**
     * 获取APP存储-私有cache文件夹
     * @param child 子目录
     */
    public static File getDataCacheDir(String child) {
        return createDir(getDataCacheDir(), child);
    }
    /**
     * 获取APP存储-私有files文件夹
     */
    public static File getDataFilesDir() {
        Context context = AppUtils.getContext();
        File appCacheDir = context.getFilesDir();
        if(appCacheDir == null) {
            String cacheDirPath = "/data/data/" + context.getPackageName() + "/files/";
            appCacheDir = new File(cacheDirPath);
        }
        return createDir(appCacheDir);
    }
    /**
     * 获取APP存储-私有files文件夹
     * @param child 子目录
     */
    public static File getDataFilesDir(String child) {
        return createDir(getDataFilesDir(), child);
    }

    /**
     * 获取可用的私有存储根目录
     * @tips 优先外置存储
     */
    public static File getUsableDir() {
        File dir = null;
        if (hasExternalStorage()) {
            dir = getExternalDir();
        }
        if (dir == null) {
            dir = getDataDir();
        }
        return dir;
    }
    /**
     * 获取可用的私有存储目录的指定子目录
     * @param child 子目录
     * @tips 优先外置存储
     */
    public static File getUsableDir(String child) {
        return createDir(getUsableDir(), child);
    }

    /**
     * 获取可用的cache目录
     * @tips 优先外置存储
     */
    public static File getUsableCacheDir() {
        File dir = null;
        if (hasExternalStorage()) {
            dir = getExternalCacheDir();
        }
        if (dir == null) {
            dir = getDataCacheDir();
        }
        return dir;
    }
    /**
     * 获取可用的cache目录
     * @param child 子目录
     * @tips 优先外置存储
     */
    public static File getUsableCacheDir(String child) {
        return createDir(getUsableCacheDir(), child);
    }

    /**
     * 获取可用的files目录
     * @tips 优先外置存储
     */
    public static File getUsableFilesDir() {
        File dir = null;
        if (hasExternalStorage()) {
            dir = getExternalFilesDir();
        }
        if (dir == null) {
            dir = getDataFilesDir();
        }
        return dir;
    }
    /**
     * 获取可用的files目录
     * @param child 子目录
     * @tips 优先外置存储
     */
    public static File getUsableFilesDir(String child) {
        return createDir(getUsableFilesDir(), child);
    }


    /**
     * 获取公共存储目录-归档路径
     * @param type 要返回的存储目录类型，不能为空
     *     {@link #DIR_MUSIC} 音乐
     *     {@link #DIR_PODCASTS} 音频
     *     {@link #DIR_RINGTONES} 铃声
     *     {@link #DIR_ALARMS} 闹钟
     *     {@link #DIR_NOTIFICATIONS} 通知
     *     {@link #DIR_PICTURES} 图片
     *     {@link #DIR_MOVIES} 电影
     *
     *     {@link #DIR_DOWNLOADS} 下载文件
     *     {@link #DIR_DCIM} 照片
     */
    public static File getPublicDir(String type) {
        return Environment.getExternalStoragePublicDirectory(type);
    }


    private static File createDir(File dir) {
        if (ObjectUtils.isEmpty(dir)) {
            return null;
        }
        dir.mkdirs();
        return dir;
    }
    private static File createDir(File parent, String child) {
        if (ObjectUtils.isEmpty(parent)) {
            return null;
        }
        if (ObjectUtils.isEmpty(child)) {
            return null;
        }
        File childDir = new File(parent, child);
        childDir.mkdir();
        return childDir;
    }


    /**
     * 是否有可用的外置存储
     */
    public static boolean hasExternalStorage() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
                && PermissionUtils.hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                && FileUtils.exists(getExternalCacheDir());
    }



    /**
     * 获取子目录
     * @param parent 父目录
     * @param child 子文件夹名
     */
    public static File getChildDir(File parent, String child) {
        if (ObjectUtils.isEmpty(parent)) {
            return null;
        }
        if (ObjectUtils.isEmpty(child)) {
            return null;
        }
        File childDir = new File(parent, child);
        childDir.mkdir();
        return childDir;
    }

    /**
     * 获取图片缓存路径
     */
    public static File getImageCacheDir(String child) {
        File parent = FileManager.getUsableCacheDir("images");
        return getChildDir(parent, child);
    }

    /**
     * 获取Http缓存路径
     */
    public static File getHttpCacheDir(String child) {
        File parent = FileManager.getUsableCacheDir("http");
        return getChildDir(parent, child);
    }

    /**
     * 获取录音缓存路径
     */
    public static File getAudioCacheDir(String child) {
        File parent = FileManager.getUsableCacheDir("audio");
        return getChildDir(parent, child);
    }

    /**
     * 获取下载缓存路径
     */
    public static File getDownloadCacheDir() {
        return FileManager.getUsableCacheDir(FileManager.DIR_DOWNLOADS);
    }

    /**
     * 获取公共资源保存路径
     */
    public static File getOwnPublicDir(String type) {
        return getChildDir(FileManager.getPublicDir(type), AppUtils.getContext().getPackageName());
    }

    /**
     * 获取视频缓存路径
     */
    public static File getVideoCacheDir() {
        return FileManager.getUsableCacheDir(FileManager.DIR_MOVIES);
    }

}
