package com.inlocal.restaurantapp.ui.bookingdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestUpdateOrderStatus implements Serializable {
    @SerializedName("orderId")
    @Expose
    private Integer orderId;
    @SerializedName("order_status")
    @Expose
    private String orderStatus;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatusId) {
        this.orderStatus = orderStatusId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
