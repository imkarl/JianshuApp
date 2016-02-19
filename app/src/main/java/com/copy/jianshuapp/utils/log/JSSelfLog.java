package com.copy.jianshuapp.utils.log;

/**
 * 自定义Log接口
 */
public interface JSSelfLog {

    /*
     * 自动生成TAG
     */
    public void i(Object... objs);
    public void d(Object... objs);
    public void e(Object... objs);
    

    /**
     * 按级别，输出格式化后的消息
     */
    public void format(JSLevel level, String format, Object... args);
    
    /**
     * 按级别，输出格式化后的消息+异常信息
     */
    public void format(JSLevel level, Throwable exception, String format, Object... args);
    
    public void log(JSLevel level, Object... messages);
    
}
