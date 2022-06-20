package com.inlocal.restaurantapp.ui.userdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ReuqestCustomerPostList implements Serializable {

    public ReuqestCustomerPostList(Integer skip, Integer limit, Integer customerId, String loginUser) {
        this.customerId = customerId;
        this.limit = limit;
        this.skip = skip;
        this.loginUser = loginUser;
    }

    @SerializedName("customer_id")
    @Expose
    private Integer customerId;
    @SerializedName("login_user_type")
    @Expose
    private String loginUser;
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

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }
}
