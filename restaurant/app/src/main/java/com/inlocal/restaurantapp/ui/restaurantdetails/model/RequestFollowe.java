package com.inlocal.restaurantapp.ui.restaurantdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestFollowe implements Serializable {

    @SerializedName("follower_id")
    @Expose
    private Integer followerId;
    @SerializedName("follower_user_type")
    @Expose
    private String followerUserType;
    @SerializedName("follow_status")
    @Expose
    private String followStatus;
    @SerializedName("login_user_type")
    @Expose
    private String loginUserType;

    public Integer getFollowerId() {
        return followerId;
    }

    public RequestFollowe(){}
    public RequestFollowe(Integer followerId, String followerUserType, String followStatus, String loginUserType) {
        this.followerId = followerId;
        this.followerUserType = followerUserType;
        this.followStatus = followStatus;
        this.loginUserType = loginUserType;
    }

    public void setFollowerId(Integer followerId) {
        this.followerId = followerId;
    }

    public String getFollowerUserType() {
        return followerUserType;
    }

    public void setFollowerUserType(String followerUserType) {
        this.followerUserType = followerUserType;
    }

    public String getFollowStatus() {
        return followStatus;
    }

    public void setFollowStatus(String followStatus) {
        this.followStatus = followStatus;
    }

    public String getLoginUserType() {
        return loginUserType;
    }

    public void setLoginUserType(String loginUserType) {
        this.loginUserType = loginUserType;
    }

}
