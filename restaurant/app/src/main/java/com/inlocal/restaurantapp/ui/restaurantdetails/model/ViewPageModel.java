package com.inlocal.restaurantapp.ui.restaurantdetails.model;


import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;

import java.io.Serializable;
import java.util.List;

public class ViewPageModel implements Serializable {
    public static final int VIEW_POST = 0;
    public static final  int VIEW_INSIGHT = 1;
    private List<FeedWallItem> myPost;
    private List<FeedWallItem> myInsight;
    private int viewType=0;

    public ViewPageModel(int viewType, List<FeedWallItem> myPost, List<FeedWallItem> myInsight) {
        this.viewType=viewType;
        this.myPost = myPost;
        this.myInsight = myInsight;
    }

    public int getVIEW_POST() {
        return VIEW_POST;
    }


    public int getVIEW_INSIGHT() {
        return VIEW_INSIGHT;
    }

    public List<FeedWallItem> getMyPost() {
        return myPost;
    }

    public void setMyPost(List<FeedWallItem> myPost) {
        this.myPost = myPost;
    }

    public List<FeedWallItem> getMyInsight() {
        return myInsight;
    }

    public void setMyInsight(List<FeedWallItem> myInsight) {
        this.myInsight = myInsight;
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }
}
