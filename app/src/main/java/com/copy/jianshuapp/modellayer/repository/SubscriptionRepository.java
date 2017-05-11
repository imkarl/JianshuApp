package com.copy.jianshuapp.modellayer.repository;

import com.copy.jianshuapp.common.LogUtils;
import com.copy.jianshuapp.common.ObjectUtils;
import com.copy.jianshuapp.modellayer.enums.SubscriptionType;
import com.copy.jianshuapp.modellayer.model.SubscriptionListEntity;
import com.copy.jianshuapp.modellayer.remote.RemoteManager;
import com.copy.jianshuapp.modellayer.remote.api.SubscriptionApi;

import java.util.Collections;
import java.util.List;

import io.reactivex.Observable;

/**
 * 订阅Repository
 * @version imkarl 2017-04
 */
public final class SubscriptionRepository extends Repository {
    private SubscriptionRepository() {
    }

    private static final SubscriptionApi subscriptionApi = RemoteManager.getInstance().createApi(SubscriptionApi.class);

    /**
     * 订阅列表
     */
    public static Observable<SubscriptionListEntity> list(SubscriptionType type, long lastUpdatedTime) {
        Observable<SubscriptionListEntity> observable = null;
        if (type != null) {
            switch (type) {
                case collection:
                    observable = list(false, "collection", lastUpdatedTime);
                    break;
                case notebook:
                    observable = list(false, "notebook", lastUpdatedTime);
                    break;
                case user:
                    observable = list(false, "user", lastUpdatedTime);
                    break;
                case push:
                    observable = list(true, null, lastUpdatedTime);
                    break;
            }
        }
        if (observable == null) {
            observable = list(false, null, lastUpdatedTime);
        }
        return observable.compose(process());
    }

    private static Observable<SubscriptionListEntity> list(boolean isOnlyPush, String type, long lastUpdatedTime) {
        Long lastUpdatedTimeObj = (lastUpdatedTime>0?lastUpdatedTime:null);
        List<String> types = (ObjectUtils.isEmpty(type) ? null : Collections.singletonList(type));
        Boolean isOnlyPushObj = (isOnlyPush ? true : null);
        return subscriptionApi.list(lastUpdatedTimeObj, types, isOnlyPushObj);
    }

}
