package com.inlocal.restaurantapp.ui.myorders.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestExportFile implements Serializable {
    public RequestExportFile(String userType) {
        this.userType = userType;
    }

    @SerializedName("userType")
    @Expose
    private String userType;

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }
}
