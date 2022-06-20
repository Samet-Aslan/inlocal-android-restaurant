package com.inlocal.restaurantapp.ui.addaddress.model.updateAddress;

import com.google.gson.annotations.SerializedName;

public class UpdateAddressRequest {

    public UpdateAddressRequest(String zipcode, String latitue, String countryCode, String address, String logitute, String city, String landmark, String country) {
        this.zipcode = zipcode;
        this.latitue = latitue;
        this.countryCode = countryCode;
        this.address = address;
        this.logitute = logitute;
        this.city = city;
        this.landmark= landmark;
        this.country= country;

    }

    @SerializedName("zipcode")
    private String zipcode;

    @SerializedName("latitue")
    private String latitue;

    @SerializedName("country_code")
    private String countryCode;

    @SerializedName("country")
    private String country;

    @SerializedName("address")
    private String address;

    @SerializedName("logitute")
    private String logitute;

    @SerializedName("city")
    private String city;

    @SerializedName("landmark")
    private String landmark;

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setLatitue(String latitue) {
        this.latitue = latitue;
    }

    public String getLatitue() {
        return latitue;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setLogitute(String logitute) {
        this.logitute = logitute;
    }

    public String getLogitute() {
        return logitute;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public String getLandmark() {
        return landmark;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}