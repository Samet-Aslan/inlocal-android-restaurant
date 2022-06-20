package com.inlocal.restaurantapp.ui.viewstory.viewmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestDeleteStory implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("story_user_type")
    @Expose
    private String storyUserType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStoryUserType() {
        return storyUserType;
    }

    public void setStoryUserType(String storyUserType) {
        this.storyUserType = storyUserType;
    }
}
