package com.inlocal.restaurantapp.ui.addcategory.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestAddCategory implements Serializable {

    public RequestAddCategory(String name) {
        this.name = name;
    }

    @SerializedName("name")
    @Expose
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
