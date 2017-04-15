package com.copy.jianshuapp.common.rx;

import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;

/**
 * 对RxTextView的一些封装
 * @version imkarl 2017-03
 */
public class RxTextViews {
    private RxTextViews() {
    }

    public static Observable<CharSequence> textChanges(TextView... views) {
        List<Observable<CharSequence>> observables = new ArrayList<>();
        for (TextView view : views) {
            observables.add(RxTextView.textChanges(view));
        }

        return Observable.merge(observables)
                .subscribeOn(AndroidSchedulers.mainThread());
    }

}
