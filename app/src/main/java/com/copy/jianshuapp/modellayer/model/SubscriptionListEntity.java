package com.copy.jianshuapp.modellayer.model;

import com.copy.jianshuapp.modellayer.enums.SubscriptionType;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 包含所有的订阅列表
 * @version imkarl 2017-04
 */
public class SubscriptionListEntity {
    @SerializedName("total_unread_count")
    private int totalUnreadCount;
    @SerializedName("enable_subscription_push")
    private boolean enableSubscriptionPush;
    @SerializedName("default_subscription_push")
    private boolean defaultSubscriptionPush;
    @SerializedName("subscriptions")
    private List<Subscription> subscriptions;

    @Override
    public String toString() {
        return "SubscriptionListEntity{" +
                "totalUnreadCount=" + totalUnreadCount +
                ", enableSubscriptionPush=" + enableSubscriptionPush +
                ", defaultSubscriptionPush=" + defaultSubscriptionPush +
                ", subscriptions=" + subscriptions +
                '}';
    }

    public int getTotalUnreadCount() {
        return totalUnreadCount;
    }

    public void setTotalUnreadCount(int totalUnreadCount) {
        this.totalUnreadCount = totalUnreadCount;
    }

    public boolean isEnableSubscriptionPush() {
        return enableSubscriptionPush;
    }

    public void setEnableSubscriptionPush(boolean enableSubscriptionPush) {
        this.enableSubscriptionPush = enableSubscriptionPush;
    }

    public boolean isDefaultSubscriptionPush() {
        return defaultSubscriptionPush;
    }

    public void setDefaultSubscriptionPush(boolean defaultSubscriptionPush) {
        this.defaultSubscriptionPush = defaultSubscriptionPush;
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(List<Subscription> subscriptions) {
        this.subscriptions = subscriptions;
    }

    public static class Subscription {
        @SerializedName("id")
        private int id;
        @SerializedName("source_identity")
        private String source_identity;
        @SerializedName("last_updated_at")
        private int lastUpdatedTime;
        @SerializedName("name")
        private String name;
        @SerializedName("latest_note_title")
        private String newNoteTitle;
        @SerializedName("image")
        private String image;
        @SerializedName("unread_count")
        private int unreadCount;
        @SerializedName("enable_push")
        private boolean enablePush;

        public SubscriptionType getSubscriptionType() {
            SubscriptionType type = null;
            if (source_identity.contains("collection")) {
                type = SubscriptionType.collection;
            } else if (source_identity.contains("notebook")) {
                type = SubscriptionType.notebook;
            } else if (source_identity.contains("user")) {
                type = SubscriptionType.user;
            } else if (source_identity.contains("push")) {
                type = SubscriptionType.push;
            }
            return type;
        }

        @Override
        public String toString() {
            return "Subscription{" +
                    "id=" + id +
                    ", source_identity='" + source_identity + '\'' +
                    ", lastUpdatedTime=" + lastUpdatedTime +
                    ", name='" + name + '\'' +
                    ", newNoteTitle='" + newNoteTitle + '\'' +
                    ", image='" + image + '\'' +
                    ", unreadCount=" + unreadCount +
                    ", enablePush=" + enablePush +
                    '}';
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getSource_identity() {
            return source_identity;
        }

        public void setSource_identity(String source_identity) {
            this.source_identity = source_identity;
        }

        public int getLastUpdatedTime() {
            return lastUpdatedTime;
        }

        public void setLastUpdatedTime(int lastUpdatedTime) {
            this.lastUpdatedTime = lastUpdatedTime;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNewNoteTitle() {
            return newNoteTitle;
        }

        public void setNewNoteTitle(String newNoteTitle) {
            this.newNoteTitle = newNoteTitle;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getUnreadCount() {
            return unreadCount;
        }

        public void setUnreadCount(int unreadCount) {
            this.unreadCount = unreadCount;
        }

        public boolean isEnablePush() {
            return enablePush;
        }

        public void setEnablePush(boolean enablePush) {
            this.enablePush = enablePush;
        }
    }
}
