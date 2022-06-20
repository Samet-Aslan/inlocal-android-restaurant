package com.inlocal.restaurantapp.ui.followers.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FollowersListingResponse implements Serializable {
    @SerializedName("MyFollowerList")
    @Expose
    private List<FollowersItem> myFollowerList = null;
    @SerializedName("skip")
    @Expose
    private Integer skip;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("total")
    @Expose
    private Integer total;


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

    public List<FollowersItem> getMyFollowerList() {
        return myFollowerList;
    }

    public void setMyFollowerList(List<FollowersItem> myFollowerList) {
        this.myFollowerList = myFollowerList;
    }
}
