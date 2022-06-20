package com.inlocal.restaurantapp.commonmodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Phone implements Serializable {
    @SerializedName("countryCode")
    private String countryCode;
    @SerializedName("number")
    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}
