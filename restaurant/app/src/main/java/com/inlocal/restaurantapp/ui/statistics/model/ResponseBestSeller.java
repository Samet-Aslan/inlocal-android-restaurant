package com.inlocal.restaurantapp.ui.statistics.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.inlocal.restaurantapp.commonmodel.MenuItem;

import java.io.Serializable;
import java.util.List;

public class ResponseBestSeller implements Serializable {
    @SerializedName("bestSellerList")
    @Expose
    private List<MenuItem> bestSellerList = null;

    public List<MenuItem> getBestSellerList() {
        return bestSellerList;
    }

    public void setBestSellerList(List<MenuItem> bestSellerList) {
        this.bestSellerList = bestSellerList;
    }
}
