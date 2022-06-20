package com.inlocal.restaurantapp.ui.userdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.inlocal.restaurantapp.commonmodel.PostItem;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;

import java.io.Serializable;
import java.util.List;

public class CustomerPostListResponse implements Serializable {
    @SerializedName("CustomerPostList")
    @Expose
    private List<FeedWallItem> customerPostList = null;
    @SerializedName("skip")
    @Expose
    private Integer skip;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("total")
    @Expose
    private Integer total;

    public List<FeedWallItem> getCustomerPostList() {
        return customerPostList;
    }

    public void setCustomerPostList(List<FeedWallItem> customerPostList) {
        this.customerPostList = customerPostList;
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
}
