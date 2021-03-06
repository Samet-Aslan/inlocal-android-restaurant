package com.inlocal.restaurantapp.ui.categorylist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CateListResponse implements Serializable {

    @SerializedName("cateListing")
    @Expose
    private List<CateListModel> cateListing = null;
    @SerializedName("skip")
    @Expose
    private Integer skip;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("total")
    @Expose
    private Integer total;

    public List<CateListModel> getCateListing() {
        return cateListing;
    }

    public void setCateListing(List<CateListModel> cateListing) {
        this.cateListing = cateListing;
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
