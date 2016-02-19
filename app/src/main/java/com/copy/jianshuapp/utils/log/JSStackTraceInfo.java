package com.copy.jianshuapp.utils.log;

/**
 * 堆栈跟踪信息
 */
public class JSStackTraceInfo {

    private JSStackTraceInfo() {
    }


    /**
     * 获取栈堆信息
     *
     * @param index
     * @return
     */
    public static JSStackTraceInfo getStackTraceInfo(int index) {
        StackTraceElement[] stacks =
                new Throwable().getStackTrace();

        if (stacks != null && stacks.length >= 0) {
            if (stacks.length < index) {
                index = stacks.length - 1;
            }
            StackTraceElement stack = stacks[index];

            JSStackTraceInfo info = new JSStackTraceInfo();
            info.className = stack.getClassName();
            info.classSimpleName = (info.className == null)
                    ? null : (info.className.indexOf('.')>0
                        ? info.className.substring(info.className.lastIndexOf('.')+1) : info.className);
            info.fileName = stack.getFileName();
            info.methodName = stack.getMethodName();
            info.lineNumber = stack.getLineNumber();
            return info;
        }
        return null;
    }

    /**
     * 从指定栈堆中，获取栈堆信息
     *
     * @param stack
     * @return
     */
    public static JSStackTraceInfo getStackTraceInfo(StackTraceElement stack) {
        if (stack != null) {
            JSStackTraceInfo info = new JSStackTraceInfo();
            info.className = stack.getClassName();
            info.classSimpleName = (info.className == null)
                    ? null : info.className.substring(info.className.lastIndexOf("") + 1);
            info.fileName = stack.getFileName();
            info.methodName = stack.getMethodName();
            info.lineNumber = stack.getLineNumber();
            return info;
        }
        return null;
    }

    /**
     * 获取当前的栈堆信息
     *
     * @return
     */
    public static JSStackTraceInfo getCurrentStackTraceInfo() {
        return getStackTraceInfo(new Throwable().getStackTrace()[1]);
    }

    /**
     * 获取调用者的栈堆信息
     *
     * @return
     */
    public static JSStackTraceInfo getCallerStackTraceInfo() {
        return getStackTraceInfo(new Throwable().getStackTrace()[2]);
    }


    private String className;
    private String classSimpleName;
    private String fileName;
    private String methodName;
    private int lineNumber;


    @Override
    public String toString() {
        return "JSStackTraceInfo [className=" + className + ", classSimpleName="
                + classSimpleName + ", fileName=" + fileName + ", methodName="
                + methodName + ", lineNumber=" + lineNumber + "]";
    }


    public String getClassName() {
        return className;
    }

    public String getFileName() {
        return fileName;
    }

    public String getMethodName() {
        return methodName;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public String getClassSimpleName() {
        return classSimpleName;
    }
}
