package com.inlocal.restaurantapp.ui.bookingdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OrderSubaddonItem implements Serializable {
    @SerializedName("addon_name")
    @Expose
    private String addonName;
    @SerializedName("sub_addon_name")
    @Expose
    private String subAddOnName;
    @SerializedName("price")
    @Expose
    private Double price;

    public OrderSubaddonItem(String subAddOnName) {
        this.subAddOnName = subAddOnName;
    }

    public String getAddonName() {
        return addonName;
    }

    public void setAddonName(String addonName) {
        this.addonName = addonName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSubAddOnName() {
        return subAddOnName;
    }

    public void setSubAddOnName(String subAddOnName) {
        this.subAddOnName = subAddOnName;
    }
}
