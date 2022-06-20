package com.inlocal.restaurantapp.ui.restaurantdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestMenuDetails implements Serializable {

    @SerializedName("menu_item_id")
    @Expose
    private Integer menuItemId;
    @SerializedName("restaurant_id")
    @Expose
    private Integer restaurantId;

    public RequestMenuDetails(Integer menuItemId, Integer restaurantId) {
        this.menuItemId = menuItemId;
        this.restaurantId = restaurantId;
    }

    public Integer getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Integer menuItemId) {
        this.menuItemId = menuItemId;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }
}
