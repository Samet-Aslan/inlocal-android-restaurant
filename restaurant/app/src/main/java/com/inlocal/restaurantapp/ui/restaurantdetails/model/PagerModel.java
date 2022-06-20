package com.inlocal.restaurantapp.ui.restaurantdetails.model;

import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;
import com.inlocal.restaurantapp.util.Constants;

import java.io.Serializable;
import java.util.List;

public class PagerModel implements Serializable {
    private int showType = Constants.ListShowType.POST;
    private List<FeedWallItem> feedWallItems;
    private int totalCount=0, pageIndex=0;

    public PagerModel(int showType, List<FeedWallItem> feedWallItems, int totalCount) {
        this.showType = showType;
        this.feedWallItems = feedWallItems;
    }

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
    }

    public List<FeedWallItem> getFeedWallItems() {
        return feedWallItems;
    }

    public void setFeedWallItems(List<FeedWallItem> feedWallItems) {
        this.feedWallItems = feedWallItems;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
}
