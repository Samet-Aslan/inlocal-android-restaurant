package com.inlocal.restaurantapp.ui.restaurantdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.inlocal.restaurantapp.ui.editprofile.model.getProfile.RestaurantDetails;

import java.io.Serializable;

public class ResponseRestauruntDetails implements Serializable {

    @SerializedName("restaurantDetails")
    @Expose
    private RestaurantDetails restaurantDetails;

    public RestaurantDetails getRestaurantDetails() {
        return restaurantDetails;
    }

    public void setRestaurantDetails(RestaurantDetails restaurantDetails) {
        this.restaurantDetails = restaurantDetails;
    }
}
