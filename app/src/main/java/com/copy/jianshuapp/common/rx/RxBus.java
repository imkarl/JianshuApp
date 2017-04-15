package com.copy.jianshuapp.common.rx;

import com.copy.jianshuapp.common.LogUtils;
import com.copy.jianshuapp.common.ObjectUtils;
import com.copy.jianshuapp.exception.NullException;

import java.util.ArrayList;
import java.util.Collection;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * 事件总线
 * @see <a href="http://blog.metova.com/how-to-use-rxjava-as-an-event-bus/">how-to-use-rxjava-as-an-event-bus</a>
 * @see <a href="https://gist.github.com/jaredsburrows/e9706bd8c7d587ea0c1114a0d7651d13">gist / jaredsburrows</a>
 */
public class RxBus {
    private RxBus() {
    }

    private static final boolean isDebug = false;

    private static final Subject<Object> sBusSubject = PublishSubject.create();

    public static boolean hasObservers() {
        return sBusSubject.hasObservers();
    }
    
    public static void post(Object event) {
        if (isDebug) LogUtils.v("RxBus post <"+getProfile(event)+">");
        if (event == null) {
            throw new NullException("'event' can not be empty.");
        }
        if (!hasObservers()) {
            if (isDebug) LogUtils.v("RxBus post error: no observer <"+getProfile(event)+">");
            return;
        }
        sBusSubject.onNext(event);
    }

    private static Observable<Object> toObservable() {
        return sBusSubject
                .observeOn(Schedulers.io())
                .onErrorReturn(throwable -> {
                    LogUtils.e(throwable);
                    return null;
                });
    }

    /**
     * 监听某个具体的字符内容
     * @param eventStr 要监听的字符内容（如："Woman"）
     */
    public static Observable<String> registerString(String eventStr) {
        if (ObjectUtils.isEmpty(eventStr)) {
            throw new NullException("'eventStr' can not be empty.");
        }
        if (isDebug) LogUtils.v("RxBus register <\""+eventStr+"\">");
        return toObservable()
                .filter(event -> {
                    if (event == null) {
                        return false;
                    }
                    boolean isEquals = event instanceof String && eventStr.equals(event);
                    if (isDebug) LogUtils.v("RxBus filter "+isEquals+" <"+getProfile(event)+"> equals <\""+eventStr+"\">");
                    return isEquals;
                })
                .map(obj -> (String) obj)
                .doOnDispose(() -> {
                    if (isDebug) LogUtils.v("RxBus unregister <\""+eventStr+"\">");
                });
    }
    /**
     * 监听具体的枚举类型
     * @param eventEnum 要监听的某个枚举值（如：SexEnum.Woman）
     */
    public static <T extends Enum<T>> Observable<T> registerEnum(Enum<T> eventEnum) {
        if (eventEnum == null) {
            throw new NullException("'eventEnum' can not be empty.");
        }
        if (isDebug) LogUtils.v("RxBus register <enum "+eventEnum+">");
        return toObservable()
                .filter(event -> {
                    if (event == null) {
                        return false;
                    }
                    boolean isEquals = event == eventEnum;
                    if (isDebug) LogUtils.v("RxBus filter "+isEquals+" <"+getProfile(event)+"> == <"+getProfile(eventEnum)+">");
                    return isEquals;
                })
                .map(obj -> (T) obj)
                .doOnDispose(() -> {
                    if (isDebug) LogUtils.v("RxBus unregister <"+getProfile(eventEnum)+">");
                });
    }
    /**
     * 监听某种类型的消息
     * @param eventClass 消息的类型（如：SexEnum.class）
     */
    public static <T> Observable<T> register(Class<T> eventClass) {
        if (eventClass == null) {
            throw new NullException("'eventClass' can not be empty.");
        }
        if (isDebug) LogUtils.v("RxBus register <"+eventClass+">");
        return toObservable()
                //.ofType(eventClass)
                .filter(event -> {
                    if (event == null) {
                        return false;
                    }
                    boolean isEquals = eventClass.equals(event.getClass()) || eventClass.isAssignableFrom(event.getClass());
                    if (isDebug) LogUtils.v("RxBus filter "+isEquals+" <"+getProfile(event)+"> isAssignableFrom <"+eventClass+">");
                    return isEquals;
                })
                .map(obj -> (T) obj)
                .doOnDispose(() -> {
                    if (isDebug) LogUtils.v("RxBus unregister <"+eventClass+">");
                });
    }

    private static String getProfile(Object obj) {
        if (obj == null) {
            return "[null]";
        }
        if (obj instanceof Enum) {
            return "enum "+obj.getClass().getName()+"."+((Enum) obj).name();
        }
        if (obj instanceof String) {
            return "\"" + obj + "\"";
        }
        if (obj instanceof Collection) {
            Collection collection = (Collection) obj;
            try {
                String itemType;
                if (collection.isEmpty()) {
                    itemType = collection.getClass().getTypeParameters()[0].getName();
                } else {
                    itemType = new ArrayList<>(collection).get(0).getClass().getName();
                }
                return collection.getClass()+"{ itemType="+itemType+", size="+collection.size()+" }";
            } catch (Throwable ignored) { }
        }
        return obj.toString();
    }

}
