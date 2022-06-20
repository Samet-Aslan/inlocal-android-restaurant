package com.inlocal.restaurantapp.ui.restaurantdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReuqestInsightList implements Serializable {
    @SerializedName("login_user_type")
    @Expose
    private String loginUserType;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("skip")
    @Expose
    private Integer skip;
    @SerializedName("restaurant_id")
    @Expose
    private Integer restaurantId;

    public ReuqestInsightList(Integer skip, Integer limit,String loginUserType, Integer restaurant_id) {
        this.loginUserType = loginUserType;
        this.limit = limit;
        this.skip = skip;
        this.restaurantId = restaurant_id;
    }
}
