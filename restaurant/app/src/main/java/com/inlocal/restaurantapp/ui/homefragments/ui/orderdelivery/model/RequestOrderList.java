package com.inlocal.restaurantapp.ui.homefragments.ui.orderdelivery.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.inlocal.restaurantapp.ui.myorders.model.DateRange;

import java.io.Serializable;

public class RequestOrderList implements Serializable {
    @SerializedName("limit")
    @Expose
    private Integer limit;

    @SerializedName("skip")
    @Expose
    private Integer skip;
    @SerializedName("userType")
    @Expose
    private String userType;
    @SerializedName("dateRange")
    @Expose
    private DateRange dateRange;

    public RequestOrderList(Integer skip,Integer limit, String userType) {
        this.limit = limit;
        this.skip = skip;
        this.userType = userType;
    }

    public RequestOrderList(Integer skip,Integer limit, String userType,DateRange dateRange) {
        this.limit = limit;
        this.skip = skip;
        this.userType = userType;
        this.dateRange = dateRange;
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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }
}
