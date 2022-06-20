package com.inlocal.restaurantapp.ui.restaurantdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestMyPostList implements Serializable {
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("skip")
    @Expose
    private Integer skip;
    @SerializedName("post_user_type")
    @Expose
    private String postUserType;
    @SerializedName("story_user_type")
    @Expose
    private String storyUserType;

    public RequestMyPostList(Integer skip,Integer limit, String postUserType, String storyUserType) {
        this.limit = limit;
        this.skip = skip;
        this.postUserType = postUserType;
        this.storyUserType = storyUserType;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public String getPostUserType() {
        return postUserType;
    }

    public void setPostUserType(String postUserType) {
        this.postUserType = postUserType;
    }

    public String getStoryUserType() {
        return storyUserType;
    }

    public void setStoryUserType(String storyUserType) {
        this.storyUserType = storyUserType;
    }
}
