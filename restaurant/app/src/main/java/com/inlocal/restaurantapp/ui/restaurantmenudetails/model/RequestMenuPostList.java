package com.inlocal.restaurantapp.ui.restaurantmenudetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestMenuPostList implements Serializable {
    @SerializedName("limit")
    @Expose
    private Integer limit;

    public Integer getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Integer menuItemId) {
        this.menuItemId = menuItemId;
    }

    public Integer getRestaurantid() {
        return restaurantid;
    }

    public void setRestaurantid(Integer restaurantid) {
        this.restaurantid = restaurantid;
    }

    @SerializedName("skip")
    @Expose
    private Integer skip;
    @SerializedName("menu_item_id")
    @Expose
    private Integer menuItemId;
    @SerializedName("restaurant_id")
    @Expose
    private Integer restaurantid;

    public RequestMenuPostList(Integer skip,Integer limit, Integer menuItemId, Integer restaurantid) {
        this.limit = limit;
        this.skip = skip;
        this.menuItemId = menuItemId;
        this.restaurantid = restaurantid;
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

}
