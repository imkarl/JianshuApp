package com.copy.jianshuapp.modellayer.repository;

import com.copy.jianshuapp.modellayer.remote.RemoteManager;
import com.copy.jianshuapp.modellayer.remote.api.NotificationApi;

import java.util.Arrays;
import java.util.List;

import io.reactivex.Observable;

/**
 * 通知Repository
 * @version imkarl 2017-04
 */
public final class NotificationRepository extends Repository {
    private NotificationRepository() {
    }

    private static final NotificationApi notificationApi = RemoteManager.getInstance().createApi(NotificationApi.class);

    /**
     * 获取所有未读数
     */

    // {"chat_message":0,"subscription":0,"user_activities":0}

    public static Observable<Object> findUnreadCounts() {
        List<String> types = Arrays.asList("user-like_something-note",
                "user-like_something-user",
                "user-share_note-note",
                "user-like_something-collection",
                "user-like_something-notebook",
                "user-comment_on_note-comment",
                "user-like_something-comment",
                "user-got_reward-lineitem",
                "user-user_created");

        return notificationApi.findUnreadCounts(types)
                .compose(process());
    }

}
