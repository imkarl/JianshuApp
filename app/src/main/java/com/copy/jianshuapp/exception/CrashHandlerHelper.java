package com.copy.jianshuapp.exception;

import com.copy.jianshuapp.common.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * APP崩溃异常捕获辅助类
 * @author alafighting 2016-02
 */
public class CrashHandlerHelper {

    private static class ExceptionHandler implements Thread.UncaughtExceptionHandler {
        /**
         * 注册监听APP未捕获异常
         */
        public static void regist(OnExceptionHandler listener) {
            Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(listener));
        }
        /**
         * 解除注册
         */
        public static void unregist() {
            Thread.UncaughtExceptionHandler handler = Thread.getDefaultUncaughtExceptionHandler();
            if (handler instanceof ExceptionHandler) {
                Thread.setDefaultUncaughtExceptionHandler(((ExceptionHandler) handler).mDefaultHandler);
            }
        }

        /** 系统默认的UncaughtException处理类 */
        private Thread.UncaughtExceptionHandler mDefaultHandler;
        private OnExceptionHandler mDefaultListener;

        private ExceptionHandler(OnExceptionHandler listener) {
            this.mDefaultListener = listener;
            this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        }

        @Override
        public void uncaughtException(Thread thread, Throwable ex) {
            if (mDefaultListener != null) {
                mDefaultListener.handlerException(ex);
            } else {
                if (mDefaultHandler != null) {
                    mDefaultHandler.uncaughtException(thread, ex);
                }
            }
        }
    }


    private static CrashHandlerHelper mCrashHandler;
    /**
     * 注册监听APP未捕获异常
     */
    public static CrashHandlerHelper regist() {
        if (mCrashHandler == null) {
            synchronized (CrashHandlerHelper.class) {
                if (mCrashHandler == null) {
                    mCrashHandler = new CrashHandlerHelper();
                    ExceptionHandler.regist(exception -> mCrashHandler.handlerException(exception));
                }
            }
        }
        return mCrashHandler;
    }
    /**
     * 解除注册
     */
    public void unregist() {
        ExceptionHandler.unregist();
        mCrashHandler = null;
    }


    private List<OnExceptionHandler> mHandlers = new ArrayList<>();

    private CrashHandlerHelper() {
    }


    /**
     * 自定义异常处理:收集错误信息&发送错误报告
     */
    private void handlerException(Throwable exception) {
        if (!mHandlers.isEmpty()) {
            for (OnExceptionHandler listener : mHandlers) {
                listener.handlerException(exception);
            }
        } else {
            // 打印APP崩溃异常
            LogUtils.e(exception);
        }
    }

    /**
     * 添加异常处理器
     */
    public void addHandler(OnExceptionHandler handler) {
        this.mHandlers.add(handler);
    }
    /**
     * 删除异常处理器
     */
    public void removeHandler(OnExceptionHandler handler) {
        this.mHandlers.remove(handler);
    }

}
