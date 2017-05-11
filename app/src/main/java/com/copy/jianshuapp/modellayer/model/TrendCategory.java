package com.copy.jianshuapp.modellayer.model;

import com.google.gson.annotations.SerializedName;

/**
 * 趋势-分类
 * @version imkarl 2017-05
 */
public class TrendCategory {

    private int id;
    private String name;
    @SerializedName("seq")
    private int position;
    @SerializedName("app_image_url")
    private String image;

    @Override
    public String toString() {
        return "TrendCategory{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", position=" + position +
                ", image='" + image + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
