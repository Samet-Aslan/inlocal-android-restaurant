package com.inlocal.restaurantapp.ui.comment.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;

import java.io.Serializable;

public class ResponsePostDetails implements Serializable {
    @SerializedName("post")
    @Expose
    private FeedWallItem postDetails;

    public FeedWallItem getPostDetails() {
        return postDetails;
    }

    public void setPostDetails(FeedWallItem postDetails) {
        this.postDetails = postDetails;
    }
}
