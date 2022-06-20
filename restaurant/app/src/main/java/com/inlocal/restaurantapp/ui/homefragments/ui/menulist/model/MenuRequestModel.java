package com.inlocal.restaurantapp.ui.homefragments.ui.menulist.model;

import com.google.gson.annotations.SerializedName;

public class MenuRequestModel {
    @SerializedName("limit")
    private int limit;
    @SerializedName("skip")
    private int skip;


    public MenuRequestModel(int limit, int skip) {
        this.limit = limit;
        this.skip = skip;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getSkip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }
}
