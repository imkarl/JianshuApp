package com.copy.jianshuapp.modellayer.remote.api.mock;

import com.copy.jianshuapp.modellayer.model.SubscriptionListEntity;
import com.copy.jianshuapp.modellayer.remote.api.SubscriptionApi;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

/**
 * @author imkarl
 */
public class MockSubscriptionApi implements SubscriptionApi {
    @Override
    public Observable<SubscriptionListEntity> list(Long lastUpdatedTimeObj, List<String> types, Boolean isOnlyPushObj) {
        SubscriptionListEntity entity = new SubscriptionListEntity();
        entity.setTotalUnreadCount(123);
        List<SubscriptionListEntity.Subscription> subscriptions = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            SubscriptionListEntity.Subscription subscription = new SubscriptionListEntity.Subscription();
            subscription.setId(i);
            subscription.setName("name-"+i);
            subscription.setImage("xxx.jpg");
            subscription.setNewNoteTitle("new-note-title-"+i);
            subscription.setSource_identity("collection");
            subscriptions.add(subscription);
        }
        entity.setSubscriptions(subscriptions);
        return Observable.just(entity);
    }
}
