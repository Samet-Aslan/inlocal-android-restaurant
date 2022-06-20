package com.inlocal.restaurantapp.ui.myorders.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ExportResponse implements Serializable {

    @SerializedName("filePath")
    @Expose
    private String filePath;

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

}
