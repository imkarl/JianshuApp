package com.copy.jianshuapp.utils.log;

import android.text.TextUtils;

/**
 * 日志打印工具类
 */
public class JSLog {
    public static final JSLevel DEBUG = JSLevel.DEBUG;
    public static final JSLevel INFO = JSLevel.INFO;
    public static final JSLevel ERROR = JSLevel.ERROR;

    private static final Integer DEFAULT_CALL_NUMBER = 4;
    
    
    private JSLog() {
    }
    
    
    /**
     * 自定义Log
     */
    public static JSSelfLog mSelfLog;
    private static String mTAG;
    private static Integer mCallNumber = DEFAULT_CALL_NUMBER;
	
    /**
     * 设置默认TAG
     * @param tag
     */
    public static void setDefaultTag(String tag) {
        JSLog.mTAG = tag;
    }
    /**
     * 获取TAG
     * @return
     */
    private static String getTag() {
        String stackInfo = getStackTraceInfo(mCallNumber);
        if (!TextUtils.isEmpty(mTAG)) {
            return mTAG + stackInfo;
        } else {
            return stackInfo;
        }
    }
    /**
     * 设置调用层次
     * @param callNumber
     */
    public static void setCallNumber(int callNumber) {
        mCallNumber = DEFAULT_CALL_NUMBER+callNumber;
    }
    
    
    /**
     * 获取栈堆信息
     * @param index
     * @return
     */
    private static String getStackTraceInfo(int index) {
		JSStackTraceInfo info = JSStackTraceInfo.getStackTraceInfo(index);
		if (info == null) {
			return "[unknown]";
		}
		
		String format = "[%s]%s.%s(L:%d)";
        String classSimpleName = info.getClassSimpleName();
        return String.format(format, getCurrentThreadName(), classSimpleName, info.getMethodName(), info.getLineNumber());
	}
	/**
	 * 获取当前线程名
	 * @return
	 */
    private static String getCurrentThreadName() {
        String threadName = Thread.currentThread().getName();
        if (!TextUtils.isEmpty(threadName) && threadName.equals("Instr: android.test.InstrumentationTestRunner")) {
            threadName = "Instr: AndroidTestCase";
        } else if (!TextUtils.isEmpty(threadName) && threadName.startsWith("OkHttp ")) {
            threadName = "OkHttp";
        }
        return threadName;
    }
	
	
	
	/*
	 * 自动生成TAG
	 */
	public static void i(Object... messages) {
        if (mSelfLog != null) {
            mSelfLog.i(messages);
        } else {
            Log.i(getTag(), messages);
        }
	}
	public static void d(Object... messages) {
        if (mSelfLog != null) {
            mSelfLog.d(messages);
        } else {
            Log.d(getTag(), messages);
        }
	}
	public static void e(Object... messages) {
        if (mSelfLog != null) {
            mSelfLog.e(messages);
        } else {
            Log.e(getTag(), messages);
        }
	}
	

    /**
     * 按级别，输出格式化后的消息
     */
	public static void format(JSLevel level, String format, Object... args) {
	    if (mSelfLog != null) {
            mSelfLog.format(level, format, args);
	    } else {
            Log.log(level, getTag(), Log.buildMessage(format, args));
        }
    }
    /**
     * 按级别，输出格式化后的消息+异常信息
     */
	public static void format(JSLevel level, Throwable exception, String format, Object... args) {
        if (mSelfLog != null) {
            mSelfLog.format(level, exception, format, args);
        } else {
            Log.log(level, getTag(), Log.buildMessage(format, args), exception);
        }
    }
	
	public static void log(JSLevel level, Object... messages) {
        if (mSelfLog != null) {
            mSelfLog.log(level, messages);
        } else {
            Log.log(level, getTag(), messages);
        }
    }

    /**
     * 将Object转换为String【便于输出】
     * @param message 消息内容
     * @return 为null时返回“<code>[null]</code>”
     */
    public static String toLogString(Object message) {
        return Log.toLogString(message);
    }
	
}
