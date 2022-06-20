package com.inlocal.restaurantapp.ui.favorites.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.inlocal.restaurantapp.commonmodel.PostItem;

import java.io.Serializable;
import java.util.List;

public class FavouriteListResponse implements Serializable {

    @SerializedName("FavoritePostList")
    @Expose
    private List<PostItem> favoritePostList = null;
    @SerializedName("skip")
    @Expose
    private Integer skip;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("total")
    @Expose
    private Integer total;

    public List<PostItem> getFavoritePostList() {
        return favoritePostList;
    }

    public void setFavoritePostList(List<PostItem> favoritePostList) {
        this.favoritePostList = favoritePostList;
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
