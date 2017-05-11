package com.copy.jianshuapp.modellayer.model;

import com.google.gson.annotations.SerializedName;

/**
 * 趋势-Banner
 * @version imkarl 2017-05
 */
public class TrendBanner {
    @SerializedName("id")
    private int id;
    @SerializedName("key")
    private String key;
    @SerializedName("name")
    private String name;
    @SerializedName("slot")
    private int position;
    @SerializedName("start_time")
    private int startTime;
    @SerializedName("end_time")
    private int endTtime;
    @SerializedName("app_image")
    private String image;
    @SerializedName("link")
    private String link;

    @Override
    public String toString() {
        return "TrendBanner{" +
                "id=" + id +
                ", key='" + key + '\'' +
                ", name='" + name + '\'' +
                ", position=" + position +
                ", startTime=" + startTime +
                ", endTtime=" + endTtime +
                ", image='" + image + '\'' +
                ", link='" + link + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getEndTtime() {
        return endTtime;
    }

    public void setEndTtime(int endTtime) {
        this.endTtime = endTtime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
