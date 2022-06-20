package com.inlocal.restaurantapp.ui.statistics.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestStatistics implements Serializable {
    public RequestStatistics(String timeSpan) {
        this.timeSpan = timeSpan;
    }

    @SerializedName("time_span")
    @Expose
    private String timeSpan;

    public String getTimeSpan() {
        return timeSpan;
    }

    public void setTimeSpan(String timeSpan) {
        this.timeSpan = timeSpan;
    }
}
