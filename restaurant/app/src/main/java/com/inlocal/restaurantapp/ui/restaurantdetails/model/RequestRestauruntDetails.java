package com.inlocal.restaurantapp.ui.restaurantdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestRestauruntDetails implements Serializable {
    public RequestRestauruntDetails(String restaurantId, String loginType) {
        this.restaurantId = restaurantId;
        this.loginUserType = loginType;
    }

    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("skip")
    @Expose
    private Integer skip;
    @SerializedName("post_id")
    @Expose
    private Integer postId;
    @SerializedName("restaurant_id")
    @Expose
    private String restaurantId;
    @SerializedName("login_user_type")
    @Expose
    private String loginUserType;
    @SerializedName("post_user_type")
    @Expose
    private String postUserType;

    public RequestRestauruntDetails(Integer skip, Integer limit, Integer postId, String postUserType, String loginUserType) {
        this.limit = limit;
        this.skip = skip;
        this.postUserType = postUserType;
        this.loginUserType = loginUserType;
        this.postId = postId;
    }

    public RequestRestauruntDetails(Integer skip, Integer limit, String restaurantId, String loginUserType) {
        this.limit = limit;
        this.skip = skip;
        this.restaurantId = restaurantId;
        this.loginUserType = loginUserType;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getLoginUserType() {
        return loginUserType;
    }

    public void setLoginUserType(String loginUserType) {
        this.loginUserType = loginUserType;
    }
}
