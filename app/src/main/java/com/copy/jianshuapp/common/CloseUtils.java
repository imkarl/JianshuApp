package com.copy.jianshuapp.common;

import java.io.Closeable;
import java.io.IOException;

/**
 * Closeable的关闭操作相关工具类
 * @version imkarl 2017-02
 */
public class CloseUtils {
    private CloseUtils() { }

    /**
     * 关闭IO
     */
    public static void closeIO(Closeable... closeables) {
        if (closeables == null) return;
        for (Closeable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (IOException e) {
                    LogUtils.w(e);
                }
            }
        }
    }

}
