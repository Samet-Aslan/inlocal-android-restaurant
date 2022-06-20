package com.inlocal.restaurantapp.ui.bookingdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestOrderDetails implements Serializable {
    public RequestOrderDetails(Integer orderId) {
        this.orderId = orderId;
    }

    @SerializedName("orderId")
    @Expose
    private Integer orderId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
