package com.copy.jianshuapp.common.rx;

import android.app.Activity;

import net.yslibrary.android.keyboardvisibilityevent.KeyboardVisibilityEvent;
import net.yslibrary.android.keyboardvisibilityevent.Unregistrar;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 键盘
 * @author imkarl 2016-10
 */
public class RxKeyboard {

    private static final class KeyboardOnSubscribe implements ObservableOnSubscribe<Boolean> {
        private Activity activity;
        private Unregistrar unregistrar;
        KeyboardOnSubscribe(Activity activity) {
            this.activity = activity;
        }
        @Override
        public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
            unregistrar = KeyboardVisibilityEvent.registerEventListener(activity, isOpen -> {
                if (!emitter.isDisposed()) {
                    emitter.onNext(isOpen);
                }
            });
            emitter.setCancellable(() -> {
                activity = null;
                unregistrar.unregister();
                unregistrar = null;
            });
        }
    }

    /**
     * 显示隐藏状态改变订阅
     */
    public static Observable<Boolean> stateChange(Activity activity) {
        return Observable.create(new KeyboardOnSubscribe(activity))
//                .flatMap(isOpen -> {
//                    Observable<Boolean> observable = Observable.just(isOpen);
//                    if (!isOpen) {
//                        observable = observable.delay(100, TimeUnit.MILLISECONDS);
//                    }
//                    return observable;
//                })
                .observeOn(AndroidSchedulers.mainThread());
    }

}
