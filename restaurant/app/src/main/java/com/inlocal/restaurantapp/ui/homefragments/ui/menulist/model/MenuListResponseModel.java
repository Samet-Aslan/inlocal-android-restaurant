package com.inlocal.restaurantapp.ui.homefragments.ui.menulist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.inlocal.restaurantapp.commonmodel.MenuItem;

import java.util.List;

public class MenuListResponseModel {
    @SerializedName("MenuItemsList")
    @Expose
    private List<MenuItem> menuItemsList = null;
    @SerializedName("skip")
    @Expose
    private Integer skip;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("total")
    @Expose
    private Integer total;

    public List<MenuItem> getMenuItemsList() {
        return menuItemsList;
    }

    public void setMenuItemsList(List<MenuItem> menuItemsList) {
        this.menuItemsList = menuItemsList;
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
