package com.copy.jianshuapp.utils.log;

/**
 * Log输出级别
 */
public enum JSLevel {

    DEBUG(android.util.Log.DEBUG),
    INFO(android.util.Log.INFO),
    ERROR(android.util.Log.ERROR);
    
    private final int level;
    public int getLevel() {
        return this.level;
    }
    private JSLevel(int level) {
        this.level = level;
    }
    
    public JSLevel valueOf(int level) {
        JSLevel theLevel = null;
        switch (level) {
        case android.util.Log.DEBUG:
            theLevel = DEBUG;
            break;
        case android.util.Log.INFO:
            theLevel = INFO;
            break;
        case android.util.Log.ERROR:
            theLevel = ERROR;
            break;
        default:
            break;
        }
        return theLevel;
    }

}
