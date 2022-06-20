package com.inlocal.restaurantapp.ui.restaurantdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.inlocal.restaurantapp.commonmodel.StoryItem;

import java.io.Serializable;
import java.util.List;

public class MyStoryListResponse implements Serializable {

    @SerializedName("MyStoryList")
    @Expose
    private List<StoryItem> myStoryList = null;
    @SerializedName("RestaurantStoryList")
    @Expose
    private List<StoryItem> myRestauruntStoryList = null;
    @SerializedName("skip")
    @Expose
    private Integer skip;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("total")
    @Expose
    private Integer total;

    public List<StoryItem> getMyStoryList() {
        return myStoryList;
    }

    public void setMyPostList(List<StoryItem> myPostList) {
        this.myStoryList = myPostList;
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

    public List<StoryItem> getMyRestauruntStoryList() {
        return myRestauruntStoryList;
    }

    public void setMyRestauruntStoryList(List<StoryItem> myRestauruntStoryList) {
        this.myRestauruntStoryList = myRestauruntStoryList;
    }
}
