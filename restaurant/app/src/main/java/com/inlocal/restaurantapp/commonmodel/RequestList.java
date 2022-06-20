package com.inlocal.restaurantapp.commonmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestList implements Serializable {

    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("skip")
    @Expose
    private Integer skip;
    @SerializedName("menu_category_id")
    @Expose
    private Integer menuCategoryId;
    @SerializedName("is_delivery")
    @Expose
    private Integer is_delivery;
    @SerializedName("restaurant_id")
    @Expose
    private Integer restaurant_id;

    public RequestList(Integer skip, Integer limit) {
        this.limit = limit;
        this.skip = skip;
    }

    public RequestList() { }

    public RequestList(Integer skip, Integer limit, Integer menuCategoryId) {
        this.limit = limit;
        this.skip = skip;
        this.menuCategoryId = menuCategoryId;
    }

    public RequestList(Integer skip, Integer limit, Integer menuCategoryId, Integer restId, Integer isDelivery) {
        this.limit = limit;
        this.skip = skip;
        this.menuCategoryId = menuCategoryId;
        this.restaurant_id = restId;
        this.is_delivery = isDelivery;

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

    public Integer getMenuCategoryId() {
        return menuCategoryId;
    }

    public void setMenuCategoryId(Integer menuCategoryId) {
        this.menuCategoryId = menuCategoryId;
    }

    public Integer getIs_delivery() {
        return is_delivery;
    }

    public void setIs_delivery(Integer is_delivery) {
        this.is_delivery = is_delivery;
    }

    public Integer getRestaurant_id() {
        return restaurant_id;
    }

    public void setRestaurant_id(Integer restaurant_id) {
        this.restaurant_id = restaurant_id;
    }
}
