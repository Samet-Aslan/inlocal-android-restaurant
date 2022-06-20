package com.inlocal.restaurantapp.ui.editprofile.model;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;
import com.inlocal.restaurantapp.commonmodel.Location;
import com.inlocal.restaurantapp.commonmodel.OpeningHoursItem;

public class ProfileUpdateRequestModel implements Serializable {

    @SerializedName("owner_email")
    private String ownerEmail;

    @SerializedName("latitue")
    private String latitue;

    @SerializedName("location")
    private Location location;

    @SerializedName("owner_name")
    private String ownerName;

    @SerializedName("address")
    private String address;

    @SerializedName("logitute")
    private String logitute;

    @SerializedName("cuisine_id")
    private Integer cuisineId;

    @SerializedName("description")
    private String description;

    @SerializedName("owner_phone")
    private String ownerPhone;

    @SerializedName("restDeliveryAvailable")
    private String restDeliveryAvailable;

    @SerializedName("zipcode")
    private String zipcode;

    @SerializedName("delivery_note")
    private String deliveryNotes;

    @SerializedName("delivery_charges")
    private Double deliveryCharges;

    @SerializedName("phone")
    private String phone;

    @SerializedName("name")
    private String name;

    @SerializedName("commission_value")
    private Integer commissionValue;

    @SerializedName("openingHours")
    private List<OpeningHoursItem> openingHours;

    @SerializedName("id")
    private Integer id;

    @SerializedName("restReservationAvailable")
    private String restReservationAvailable;

    @SerializedName("email")
    private String email;

    @SerializedName("commission_type")
    private String commissionType;

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setLatitue(String latitue) {
        this.latitue = latitue;
    }

    public String getLatitue() {
        return latitue;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerName() {
        return ownerName;
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

    public void setCuisineId(Integer cuisineId) {
        this.cuisineId = cuisineId;
    }

    public Integer getCuisineId() {
        return cuisineId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setOwnerPhone(String ownerPhone) {
        this.ownerPhone = ownerPhone;
    }

    public String getOwnerPhone() {
        return ownerPhone;
    }

    public void setRestDeliveryAvailable(String restDeliveryAvailable) {
        this.restDeliveryAvailable = restDeliveryAvailable;
    }

    public String getRestDeliveryAvailable() {
        return restDeliveryAvailable;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setDeliveryCharges(Double deliveryCharges) {
        this.deliveryCharges = deliveryCharges;
    }

    public Double getDeliveryCharges() {
        return deliveryCharges;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setCommissionValue(Integer commissionValue) {
        this.commissionValue = commissionValue;
    }

    public Integer getCommissionValue() {
        return commissionValue;
    }

    public void setOpeningHours(List<OpeningHoursItem> openingHours) {
        this.openingHours = openingHours;
    }

    public List<OpeningHoursItem> getOpeningHours() {
        return openingHours;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setRestReservationAvailable(String restReservationAvailable) {
        this.restReservationAvailable = restReservationAvailable;
    }

    public String getRestReservationAvailable() {
        return restReservationAvailable;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setCommissionType(String commissionType) {
        this.commissionType = commissionType;
    }

    public String getCommissionType() {
        return commissionType;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getDeliveryNotes() {
        return deliveryNotes;
    }

    public void setDeliveryNotes(String deliveryNotes) {
        this.deliveryNotes = deliveryNotes;
    }
}