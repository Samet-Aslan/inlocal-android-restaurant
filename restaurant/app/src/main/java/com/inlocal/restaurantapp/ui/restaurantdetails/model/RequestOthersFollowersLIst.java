package com.inlocal.restaurantapp.ui.restaurantdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestOthersFollowersLIst implements Serializable {
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("skip")
    @Expose
    private Integer skip;
    @SerializedName("other_user_id")
    @Expose
    private Integer otherUserId;
    @SerializedName("other_user_type")
    @Expose
    private String otherUserType;
    @SerializedName("list_type")
    @Expose
    private String listType;
/*
    public RequestOthersFollowersLIst(Integer skip, Integer limit, Integer otherUserId, String otherUserType, String listType) {
        this.limit = limit;
        this.skip = skip;
        this.otherUserId = otherUserId;
        this.otherUserType = otherUserType;
        this.listType = listType;
    }*/

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

    public Integer getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(Integer otherUserId) {
        this.otherUserId = otherUserId;
    }

    public String getOtherUserType() {
        return otherUserType;
    }

    public void setOtherUserType(String otherUserType) {
        this.otherUserType = otherUserType;
    }

    public String getListType() {
        return listType;
    }

    public void setListType(String listType) {
        this.listType = listType;
    }

}
