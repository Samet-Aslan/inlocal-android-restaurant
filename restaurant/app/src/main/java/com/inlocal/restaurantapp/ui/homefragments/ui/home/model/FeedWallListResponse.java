package com.inlocal.restaurantapp.ui.homefragments.ui.home.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class FeedWallListResponse implements Serializable {
    @SerializedName("MyFeedWallPosts")
    @Expose
    private List<FeedWallItem> myFeedWallPosts = null;
    @SerializedName("skip")
    @Expose
    private Integer skip;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("total")
    @Expose
    private Integer total;

    public List<FeedWallItem> getMyFeedWallPosts() {
        return myFeedWallPosts;
    }

    public void setMyFeedWallPosts(List<FeedWallItem> myFeedWallPosts) {
        this.myFeedWallPosts = myFeedWallPosts;
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
