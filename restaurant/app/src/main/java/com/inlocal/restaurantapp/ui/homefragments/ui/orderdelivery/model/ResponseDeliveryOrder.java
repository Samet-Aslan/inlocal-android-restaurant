package com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ResponseDeliveryOrder implements Serializable {
    @SerializedName("orderList")
    @Expose
    private List<DeliveryOrderItem> orderList = null;
    @SerializedName("skip")
    @Expose
    private Integer skip;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("reservationOrderCount")
    @Expose
    private Integer reservationOrderCount;
    @SerializedName("deliveryOrderCount")
    @Expose
    private Integer deliveryOrderCount;

    public List<DeliveryOrderItem> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<DeliveryOrderItem> orderList) {
        this.orderList = orderList;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getReservationOrderCount() {
        return reservationOrderCount;
    }

    public void setReservationOrderCount(Integer reservationOrderCount) {
        this.reservationOrderCount = reservationOrderCount;
    }

    public Integer getDeliveryOrderCount() {
        return deliveryOrderCount;
    }

    public void setDeliveryOrderCount(Integer deliveryOrderCount) {
        this.deliveryOrderCount = deliveryOrderCount;
    }
}
