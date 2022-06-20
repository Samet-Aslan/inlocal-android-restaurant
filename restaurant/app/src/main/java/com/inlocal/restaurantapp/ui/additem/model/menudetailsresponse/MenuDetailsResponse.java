package com.inlocal.restaurantapp.ui.additem.model.menudetailsresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MenuDetailsResponse implements Serializable {

    @SerializedName("MenuItemDetails")
    @Expose
    private MenuItemDetails menuItemDetails;

    public MenuItemDetails getMenuItemDetails() {
        return menuItemDetails;
    }

    public void setMenuItemDetails(MenuItemDetails menuItemDetails) {
        this.menuItemDetails = menuItemDetails;
    }

    @SerializedName("skip")
    @Expose
    private Integer skip;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("total")
    @Expose
    private Integer total;

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
