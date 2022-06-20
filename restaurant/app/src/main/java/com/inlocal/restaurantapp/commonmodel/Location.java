package com.inlocal.restaurantapp.commonmodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Location implements Serializable {
    @SerializedName("latitue")
    private Double latitue;
    @SerializedName("logitute")
    private Double logitute;

    public Location(Double latitue, Double logitute) {
        this.latitue = latitue;
        this.logitute = logitute;
    }

    public Double getLogitute() {
        return logitute;
    }

    public void setLogitute(Double logitute) {
        this.logitute = logitute;
    }

    public Double getLatitue() {
        return latitue;
    }

    public void setLatitue(Double latitue) {
        this.latitue = latitue;
    }
}
