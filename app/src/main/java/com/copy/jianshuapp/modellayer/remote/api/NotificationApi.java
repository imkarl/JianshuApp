package com.copy.jianshuapp.modellayer.remote.api;

import java.util.List;

import io.reactivex.Observable;

/**
 * 通知API
 * @author imkarl
 */
public interface NotificationApi {

    Observable<Object> findUnreadCounts(List<String> types);

}
