package com.inlocal.restaurantapp.ui.restaurantdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.inlocal.restaurantapp.commonmodel.PostItem;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;

import java.io.Serializable;
import java.util.List;

public class MyPostResponse implements Serializable {

    @SerializedName("MyPostList")
    @Expose
    private List<FeedWallItem> myPostList = null;
    @SerializedName("RestaurantPostList")
    @Expose
    private List<FeedWallItem> restaruntPostList = null;
    @SerializedName("insightPostList")
    @Expose
    private List<FeedWallItem> insightPostList = null;
    @SerializedName("skip")
    @Expose
    private Integer skip;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("total")
    @Expose
    private Integer total;

    public List<FeedWallItem> getMyPostList() {
        return myPostList;
    }

    public void setMyPostList(List<FeedWallItem> myPostList) {
        this.myPostList = myPostList;
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

    public List<FeedWallItem> getRestaruntPostList() {
        return restaruntPostList;
    }

    public void setRestaruntPostList(List<FeedWallItem> restaruntPostList) {
        this.restaruntPostList = restaruntPostList;
    }

    public List<FeedWallItem> getInsightPostList() {
        return insightPostList;
    }

    public void setInsightPostList(List<FeedWallItem> insightPostList) {
        this.insightPostList = insightPostList;
    }
}
