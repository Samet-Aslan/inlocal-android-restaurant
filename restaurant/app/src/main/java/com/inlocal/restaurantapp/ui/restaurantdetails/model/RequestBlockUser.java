package com.inlocal.restaurantapp.ui.restaurantdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestBlockUser implements Serializable {
    public RequestBlockUser(Integer blockedUserId, String blockedUserType, String loginUserType) {
        this.blockedUserId = blockedUserId;
        this.blockedUserType = blockedUserType;
        this.loginUserType = loginUserType;
    }

    @SerializedName("blocked_user_id")
    @Expose
    private Integer blockedUserId;
    @SerializedName("blocked_user_type")
    @Expose
    private String blockedUserType;
    @SerializedName("login_user_type")
    @Expose
    private String loginUserType;

    public Integer getBlockedUserId() {
        return blockedUserId;
    }

    public void setBlockedUserId(Integer blockedUserId) {
        this.blockedUserId = blockedUserId;
    }

    public String getBlockedUserType() {
        return blockedUserType;
    }

    public void setBlockedUserType(String blockedUserType) {
        this.blockedUserType = blockedUserType;
    }

    public String getLoginUserType() {
        return loginUserType;
    }

    public void setLoginUserType(String loginUserType) {
        this.loginUserType = loginUserType;
    }
}
