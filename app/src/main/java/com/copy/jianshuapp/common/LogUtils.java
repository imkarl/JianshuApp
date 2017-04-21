package com.copy.jianshuapp.common;

import android.util.Log;

import com.copy.jianshuapp.BuildConfig;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Locale;

import okhttp3.Interceptor;

/**
 * 日志打印
 * @version imkarl 2016-08
 * @see <a href="https://github.com/zserge/log">zserge/log</a>
 */
public final class LogUtils {
    private LogUtils() {}

    public interface Printer {
        void print(int level, String tag, String msg);
    }

    private static class AndroidPrinter implements Printer {
        public void print(int level, String tag, String msg) {
            Log.println(level, tag, msg);
        }
    }

    /*
     * 默认配置
     */
    private static final String DEFAULT_TAG = "CopyJianshu";
    private static final Printer DEFAULT_PRINTER = new AndroidPrinter();
    private static final int DEFAULT_MIN_LEVEL = LogUtils.VERBOSE;
    private static final int MAX_LOG_LINE_LENGTH = 3000;
    private static final boolean isDebug = BuildConfig.DEBUG;


    public final static int VERBOSE = Log.VERBOSE;
    public final static int DEBUG = Log.DEBUG;
    public final static int INFO = Log.INFO;
    public final static int WARN = Log.WARN;
    public final static int ERROR = Log.ERROR;

    private static Printer mPrinter = DEFAULT_PRINTER;
    private static String mTag = DEFAULT_TAG;
    private static int mMinLevel = DEFAULT_MIN_LEVEL;

    public static synchronized LogUtils usePrinter(Printer printer) {
        mPrinter = printer;
        return null;
    }
    public static synchronized LogUtils useTag(String tag) {
        mTag = tag;
        return null;
    }
    public static synchronized LogUtils minLevel(int minLevel) {
        mMinLevel = minLevel;
        return null;
    }


    public static void v(Object msg) {
        log(VERBOSE, msg);
    }
    public static void d(Object msg) {
        log(DEBUG, msg);
    }
    public static void i(Object msg) {
        log(INFO, msg);
    }
    public static void w(Object msg) {
        log(WARN, msg);
    }
    public static void e(Object msg) {
        log(ERROR, msg);
    }

    /**
     * 输出所有调用堆栈记录
     */
    public static void logAllStack(String mark) {
        for (StackTraceElement element : new Throwable().getStackTrace()) {
            String className = element.getClassName();
            className = className.substring(className.lastIndexOf(".") + 1);
            String codeLine = className+'.'+element.getMethodName()+'('+element.getFileName()+':'+element.getLineNumber()+')';
            mPrinter.print(DEBUG, mTag, codeLine);
        }
        mPrinter.print(DEBUG, mTag, "\t" + mark);
    }

    private static void log(int level, Object msg) {
        if (!isDebug && level < LogUtils.WARN) {
            return;
        }
        if (level < mMinLevel) {
            return;
        }
        if (mPrinter == null) {
            return;
        }

        StackTraceElement element = new Throwable().getStackTrace()[2];
        print(level, element, mTag, toString(msg));
    }
    private static void print(int level, StackTraceElement element, String tag, Object msg) {
        String codeLine;
        if (element != null) {
            String className = element.getClassName();
            className = className.substring(className.lastIndexOf(".") + 1);
            codeLine = String.format(Locale.getDefault(), "[%s] %s.%s(%s:%s)", Thread.currentThread().getName(), className, element.getMethodName(), element.getFileName(), element.getLineNumber());
        } else {
            codeLine = String.format(Locale.getDefault(), "[%s] Unknown(Unknown.java:0)", Thread.currentThread().getName());
        }
        mPrinter.print(level, tag, codeLine);

        String message = LogUtils.toString(msg);
        for (String line : message.split("\n")) {
            do {
                int splitPos = Math.min(MAX_LOG_LINE_LENGTH, line.length());
                String part = line.substring(0, splitPos);
                line = line.substring(splitPos);

                if (filter(part)) {
                    mPrinter.print(level, tag, "\t" + part);
                }
            } while (line.length() > 0);
        }
    }
    private static boolean filter(String part) {
        return !contains(
                part
                ,"rx.internal"
                ,"rx.Subscriber"
                ,"okio.RealBufferedSource"
                ,"okhttp3.internal"
                ,"okhttp3.RealCall"
                ,"com.facebook.stetho.okhttp"
                ,"com.android.internal.os.ZygoteInit"
                ,"android.app.ActivityThread.acces"
                ,"InetAddress.getAllByNameImpl"
                ,"Posix.android_getaddrinfo"
                ,"okio.AsyncTimeout"
                ,"internal.policy.PhoneWindow"
                ,"View.updateDisplayListIfDirty"
                ,"android.app.ActivityThread.-wrap"
                ,"android.app.ActivityThread$H"
                ,"at android.view.ViewRootImpl"
                ,"at io.reactivex.internal"
        );
    }
    private static boolean contains(String text, String... strs) {
        for (String str : strs) {
            if (text.contains(str)) {
                return true;
            }
        }
        return false;
    }
    private static String toString(Object msg) {
        String message;

        if (msg == null) {
            message = "[null]";
        } else if (msg.getClass().isArray()) {
            message = arrayToString(msg);
        } else if (msg instanceof Enum) {
            Enum enumObj = (Enum) msg;
            String className = enumObj.getClass().getSimpleName();
            if (ObjectUtils.isEmpty(className)) {
                className = enumObj.getClass().getName();
                if (className.contains(".")) {
                    className = className.substring(className.lastIndexOf(".") + 1);
                }
                if (className.contains("$")) {
                    className = className.substring(0, className.lastIndexOf("$"));
                }
            }
            message = className+"."+enumObj.name();
        } else if (msg instanceof Interceptor.Chain) {
            Interceptor.Chain chain = (Interceptor.Chain) msg;
            message = "Interceptor.Chain{" +
                    "request=" + chain.request() +
                    ", connection=" + chain.connection() +
                    '}';
        } else if (msg instanceof Throwable) {
            Throwable tr = (Throwable) msg;
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            try {
                tr.printStackTrace(pw);
            } catch (OutOfMemoryError ignored) {}
            pw.flush();
            message = sw.toString();
        } else {
            message = String.valueOf(msg);
        }

        if (ObjectUtils.isEmpty(message)) {
            message = "[ ]";
        }

        return message;
    }

    private static String arrayToString(Object msg) {
        if (msg instanceof int[]) {
            return Arrays.toString((int[]) msg);
        } else if (msg instanceof long[]) {
            return Arrays.toString((long[]) msg);
        } else if (msg instanceof short[]) {
            return Arrays.toString((short[]) msg);
        } else if (msg instanceof char[]) {
            return Arrays.toString((char[]) msg);
        } else if (msg instanceof byte[]) {
            return Arrays.toString((byte[]) msg);
        } else if (msg instanceof boolean[]) {
            return Arrays.toString((boolean[]) msg);
        } else if (msg instanceof float[]) {
            return Arrays.toString((float[]) msg);
        } else if (msg instanceof double[]) {
            return Arrays.toString((double[]) msg);
        } else if (msg instanceof Object[]) {
            return Arrays.toString((Object[]) msg);
        }
        return String.valueOf(msg);
    }

}
