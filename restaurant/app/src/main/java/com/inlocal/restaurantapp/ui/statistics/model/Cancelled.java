package com.inlocal.restaurantapp.ui.statistics.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cancelled implements Serializable {
    @SerializedName("time_span")
    @Expose
    private Integer today;
    @SerializedName("Total")
    @Expose
    private Integer total;

    public Integer getToday() {
        return today;
    }

    public void setToday(Integer today) {
        this.today = today;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
