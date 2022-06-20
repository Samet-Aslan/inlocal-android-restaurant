package com.inlocal.restaurantapp.ui.favorites.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequesFavouritesList implements Serializable {
    @SerializedName("login_user_type")
    @Expose
    private String loginUserType;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("skip")
    @Expose
    private Integer skip;

    public RequesFavouritesList(Integer skip, Integer limit,  String postUserType,String loginUserType) {
        this.loginUserType = loginUserType;
        this.limit = limit;
        this.skip = skip;
        this.postUserType = postUserType;
    }

    public RequesFavouritesList(Integer skip, Integer limit,String loginUserType) {
        this.loginUserType = loginUserType;
        this.limit = limit;
        this.skip = skip;
        this.postUserType = postUserType;
    }

    @SerializedName("post_user_type")
    @Expose
    private String postUserType;

    public String getLoginUserType() {
        return loginUserType;
    }

    public void setLoginUserType(String loginUserType) {
        this.loginUserType = loginUserType;
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

}
