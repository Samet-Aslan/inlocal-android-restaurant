package com.inlocal.restaurantapp.ui.followers.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestFollowersList implements Serializable {
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("skip")
    @Expose
    private Integer skip;
    @SerializedName("login_user_type")
    @Expose
    private String loginUserType;

    public RequestFollowersList(Integer skip,Integer limit, String loginUserType) {
        this.limit = limit;
        this.skip = skip;
        this.loginUserType = loginUserType;
    }

    public String getLoginUserType() {
        return loginUserType;
    }

    public void setLoginUserType(String loginUserType) {
        this.loginUserType = loginUserType;
    }
}
