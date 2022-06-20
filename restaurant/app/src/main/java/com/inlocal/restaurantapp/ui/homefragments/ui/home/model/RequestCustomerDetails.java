package com.inlocal.restaurantapp.ui.homefragments.ui.home.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestCustomerDetails implements Serializable {
    public RequestCustomerDetails(String customerId) {
        this.customerId = customerId;
    }

    public RequestCustomerDetails(String customerId, String loginUserType) {
        this.customerId = customerId;
        this.loginUserType = loginUserType;
    }

    @SerializedName("customer_id")
    @Expose
    private String customerId;
    @SerializedName("login_user_type")
    @Expose
    private String loginUserType;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("skip")
    @Expose
    private Integer skip;

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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getLoginUserType() {
        return loginUserType;
    }

    public void setLoginUserType(String loginUserType) {
        this.loginUserType = loginUserType;
    }
}
