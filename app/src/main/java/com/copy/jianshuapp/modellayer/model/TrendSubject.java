package com.copy.jianshuapp.modellayer.model;

import com.google.gson.annotations.SerializedName;

/**
 * 趋势-主题
 * @version imkarl 2017-05
 */
public class TrendSubject {
    private int id;
    private String title;
    @SerializedName("is_subscribed")
    private boolean subscribed;

    @Override
    public String toString() {
        return "TrendSubject{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", subscribed=" + subscribed +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }
}
