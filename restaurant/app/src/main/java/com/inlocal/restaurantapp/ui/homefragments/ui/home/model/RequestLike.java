package com.inlocal.restaurantapp.ui.homefragments.ui.home.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestLike implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("login_user_type")
    @Expose
    private String loginUserType;
    @SerializedName("like_status")
    @Expose
    private String likeStatus;

    public RequestLike(Integer id, String loginUserType, String likeStatus) {
        this.id = id;
        this.loginUserType = loginUserType;
        this.likeStatus = likeStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoginUserType() {
        return loginUserType;
    }

    public void setLoginUserType(String loginUserType) {
        this.loginUserType = loginUserType;
    }

    public String getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(String likeStatus) {
        this.likeStatus = likeStatus;
    }
}
