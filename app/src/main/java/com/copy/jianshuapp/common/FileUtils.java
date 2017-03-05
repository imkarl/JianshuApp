package com.copy.jianshuapp.common;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 文件操作相关
 * @version imkarl 2017-02
 */ 
public class FileUtils {
    private FileUtils() {}

    /**
     * 判断目录是否存在，不存在则成功
     * @param dir 文件夹
     * @return {@code true}: 存在或创建成功<br>{@code false}: 不存在或创建失败
     */
    public static boolean createDir(File dir) {
        // 如果存在，是目录则返回true，是文件则返回false，不存在则返回是否创建成功
        return dir != null && (dir.exists() ? dir.isDirectory() : dir.mkdirs());
    }
    /**
     * 判断文件是否存在，存在则在创建之前删除
     * @param file 文件
     * @return {@code true}: 创建成功<br>{@code false}: 创建失败
     */
    public static boolean createFile(File file) {
        if (file == null) return false;
        // 文件存在并且删除失败返回false
        if (file.exists() && file.isFile() && !file.delete()) return false;
        // 创建目录失败返回false
        if (!createDir(file.getParentFile())) return false;
        try {
            return file.createNewFile();
        } catch (IOException e) {
            LogUtils.w(e);
            return false;
        }
    }

    /**
     * 删除文件
     */
    public static boolean deleteFile(File file) {
        if (!exists(file)) {
            return true;
        }
        return file.delete();
    }
    /**
     * 删除目录（包括其下的所有文件）
     * @param dir 目录
     * @return {@code true}: 删除成功<br>{@code false}: 删除失败
     */
    public static boolean deleteDir(File dir) {
        if (dir == null) return false;
        // 目录不存在返回true
        if (!dir.exists()) return true;
        // 不是目录返回false
        if (!dir.isDirectory()) return false;
        // 现在文件存在且是文件夹
        File[] files = dir.listFiles();
        if (files != null && files.length != 0) {
            for (File file : files) {
                if (file.isFile()) {
                    if (!deleteFile(file)) return false;
                } else if (file.isDirectory()) {
                    if (!deleteDir(file)) return false;
                }
            }
        }
        return true;
    }

    /**
     * 复制文件
     */
    public static boolean copyFile(File srcFile, File destFile) {
        if (srcFile == null || destFile == null) return false;
        // 源文件不存在或者不是文件则返回false
        if (!srcFile.exists() || !srcFile.isFile()) return false;
        // 目标文件存在且是文件则返回false
        if (destFile.exists() && destFile.isFile()) return false;
        // 目标目录不存在返回false
        if (!createDir(destFile.getParentFile())) return false;
        try {
            return writeFileFromIS(destFile, new FileInputStream(srcFile), false)
                    && (true || deleteFile(srcFile));
        } catch (FileNotFoundException e) {
            LogUtils.e(e);
            return false;
        }
    }
    /**
     * 复制文件夹
     */
    public static boolean copyDir(File srcDir, File targetDir) {
        if (srcDir == null || targetDir == null) return false;
        // 如果目标目录在源目录中则返回false
        if (srcDir.equals(targetDir)) return true;
        // 源文件不存在或者不是目录则返回false
        if (!srcDir.exists() || !srcDir.isDirectory()) return false;
        // 目标目录不存在返回false
        if (!createDir(targetDir)) return false;
        File[] files = srcDir.listFiles();
        for (File file : files) {
            File childTargetFile = new File(targetDir, file.getName());
            if (file.isFile()) {
                // 如果操作失败返回false
                if (!copyFile(file, childTargetFile)) return false;
            } else if (file.isDirectory()) {
                // 如果操作失败返回false
                if (!copyDir(file, childTargetFile)) return false;
            }
        }
        return true;
    }
    /**
     * 移动目录
     *
     * @param srcDir  源目录
     * @param targetDir 目标目录
     * @return {@code true}: 移动成功<br>{@code false}: 移动失败
     */
    public static boolean moveDir(File srcDir, File targetDir) {
        return copyDir(srcDir, targetDir) && deleteDir(targetDir);
    }

    /**
     * 将输入流写入文件
     * @param file   文件
     * @param is     输入流
     * @param append 是否追加在文件末
     * @return {@code true}: 写入成功<br>{@code false}: 写入失败
     */
    public static boolean writeFileFromIS(File file, InputStream is, boolean append) {
        if (file == null || is == null) return false;
        if (!createFile(file)) return false;
        OutputStream os = null;
        try {
            os = new BufferedOutputStream(new FileOutputStream(file, append));
            byte data[] = new byte[1024];
            int len;
            while ((len = is.read(data, 0, 1024)) != -1) {
                os.write(data, 0, len);
            }
            return true;
        } catch (IOException e) {
            LogUtils.w(e);
            return false;
        } finally {
            CloseUtils.closeIO(is, os);
        }
    }

    /*
     * 获取文件MD5
     */
    public static String md5(File file) {
        if (!exists(file)) {
            return null;
        }
        byte[] byteMD5 = EncryptUtils.encryptMD5File(file);
        if (byteMD5 == null) {
            return null;
        }
        return ConvertUtils.bytes2HexString(byteMD5);
    }

    /*
     * 比较2个文件是否相同(对比MD5)
     */
    public static boolean equals(File src, File target) {
        if ((src == target) || (src != null && src.equals(target))) {
            return true;
        }
        if (!exists(src) && !exists(target)) {
            return true;
        }

        String srcMd5 = FileUtils.md5(src);
        String targetMd5 = FileUtils.md5(target);
        return srcMd5 != null && srcMd5.equals(targetMd5);
    }

    /**
     * 判断文件是否存在
     */
    public static boolean exists(File file) {
        return file != null && file.exists();
    }

}
