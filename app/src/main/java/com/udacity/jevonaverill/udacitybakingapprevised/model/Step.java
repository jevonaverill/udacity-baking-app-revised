package com.udacity.jevonaverill.udacitybakingapprevised.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by jevonaverill on 9/6/17.
 */

public class Step implements Serializable {

    private int id;
    private String shortDescription;
    private String description;

    @SerializedName("videoURL")
    private String videoUrl;

    @SerializedName("thumbnailURL")
    private String thumbnailUrl;

    public Step() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return "\nStep{" +
                "\n\tid=" + id +
                ", \n\tshortDescription='" + shortDescription + '\'' +
                ", \n\tdescription='" + description + '\'' +
                ", \n\tvideoUrl='" + videoUrl + '\'' +
                ", \n\tthumbnailUrl='" + thumbnailUrl + '\'' +
                '}';
    }
}
