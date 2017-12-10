package com.copy.jianshuapp.modellayer.remote.api;

import com.copy.jianshuapp.modellayer.model.SubscriptionListEntity;

import java.util.List;

import io.reactivex.Observable;

/**
 * 订阅API
 * @author imkarl
 */
public interface SubscriptionApi {

    Observable<SubscriptionListEntity> list(Long lastUpdatedTimeObj, List<String> types, Boolean isOnlyPushObj);

}
