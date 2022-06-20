package com.inlocal.restaurantapp.ui.myorders.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderHistoryItem implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("percentage_tip_value")
    @Expose
    private Integer percentageTipValue;
    @SerializedName("tip_amt")
    @Expose
    private Double tipAmt;
    @SerializedName("final_order_amount")
    @Expose
    private Double finalOrderAmount;
    @SerializedName("order_type")
    @Expose
    private String orderType;
    @SerializedName("order_date")
    @Expose
    private String orderDate;
    @SerializedName("date_time")
    @Expose
    private Object dateTime;
    @SerializedName("booking_id")
    @Expose
    private Integer bookingId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPercentageTipValue() {
        return percentageTipValue;
    }

    public void setPercentageTipValue(Integer percentageTipValue) {
        this.percentageTipValue = percentageTipValue;
    }

    public Double getFinalOrderAmount() {
        return finalOrderAmount;
    }

    public void setFinalOrderAmount(Double finalOrderAmount) {
        this.finalOrderAmount = finalOrderAmount;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Object getDateTime() {
        return dateTime;
    }

    public void setDateTime(Object dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getBookingId() {
        return bookingId;
    }

    public void setBookingId(Integer bookingId) {
        this.bookingId = bookingId;
    }

    public Double getTipAmt() {
        return tipAmt;
    }

    public void setTipAmt(Double tipAmt) {
        this.tipAmt = tipAmt;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
}
