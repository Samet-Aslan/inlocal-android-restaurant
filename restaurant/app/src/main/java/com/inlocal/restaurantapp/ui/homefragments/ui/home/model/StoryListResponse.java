package com.inlocal.restaurantapp.ui.homefragments.ui.home.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.inlocal.restaurantapp.commonmodel.StoryItem;

import java.io.Serializable;
import java.util.List;

public class StoryListResponse implements Serializable {

    @SerializedName("MyFeedWallStories")
    @Expose
    private List<StoryItem> myStoryList = null;
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

    public void setMyStoryList(List<StoryItem> myStoryList) {
        this.myStoryList = myStoryList;
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

    public StoryListResponse(List<StoryItem> myStoryList, Integer skip, Integer limit, Integer total) {
        this.myStoryList = myStoryList;
        this.skip = skip;
        this.limit = limit;
        this.total = total;
    }
}
