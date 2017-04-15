package com.copy.jianshuapp.common.events;

/**
 * 用户事件
 * @version imkarl 2017-04
 */
public enum UserEvent {

    /** 登陆成功 */
    LOGIN,
    /** 登陆失效 */
    INVALID,
    /** 退出登陆 */
    LOGOUT,
    /** 被迫下线*/
    BE_COERCED_OUT;

}
