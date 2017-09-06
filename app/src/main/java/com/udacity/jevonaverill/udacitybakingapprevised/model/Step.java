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

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
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
