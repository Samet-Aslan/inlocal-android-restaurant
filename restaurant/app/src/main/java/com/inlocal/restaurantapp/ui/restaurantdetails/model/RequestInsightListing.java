package com.inlocal.restaurantapp.ui.restaurantdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestInsightListing implements Serializable {

    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("skip")
    @Expose
    private Integer skip;
    @SerializedName("post_id")
    @Expose
    private Integer postId;

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

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getLoginUserType() {
        return loginUserType;
    }

    public void setLoginUserType(String loginUserType) {
        this.loginUserType = loginUserType;
    }

    public String getPostUserType() {
        return postUserType;
    }

    public void setPostUserType(String postUserType) {
        this.postUserType = postUserType;
    }

    @SerializedName("restaurant_id")
    @Expose
    private Integer restaurantId;
    @SerializedName("login_user_type")
    @Expose
    private String loginUserType;
    @SerializedName("post_user_type")
    @Expose
    private String postUserType;

    public RequestInsightListing( Integer skip, Integer limit, Integer postId, Integer restaurantId,String postUserType, String loginUserType) {
        this.limit = limit;
        this.skip = skip;
        this.postUserType = postUserType;
        this.restaurantId = restaurantId;
        this.loginUserType = loginUserType;
        this.postId = postId;
    }
}
