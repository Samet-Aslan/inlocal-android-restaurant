package com.inlocal.restaurantapp.ui.homefragments.ui.home.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestFavourites implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("login_user_type")
    @Expose
    private String loginUserType;
    @SerializedName("favorite_status")
    @Expose
    private String favoritesStatus;

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

    public String getFavoritesStatus() {
        return favoritesStatus;
    }

    public void setFavoritesStatus(String favoritesStatus) {
        this.favoritesStatus = favoritesStatus;
    }

    public RequestFavourites(Integer id, String loginUserType, String favoritesStatus) {
        this.id = id;
        this.loginUserType = loginUserType;
        this.favoritesStatus = favoritesStatus;
    }
}
