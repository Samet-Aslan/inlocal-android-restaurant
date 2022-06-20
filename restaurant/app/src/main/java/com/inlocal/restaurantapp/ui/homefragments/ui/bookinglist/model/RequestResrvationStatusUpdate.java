package com.inlocal.restaurantapp.ui.homefragments.ui.bookinglist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestResrvationStatusUpdate implements Serializable {


    @SerializedName("reservationId")
    @Expose
    private Integer reservationId;
    @SerializedName("booking_status")
    @Expose
    private String bookingStatus;
    @SerializedName("message")
    @Expose
    private String message;

    public Integer getReservationId() {
        return reservationId;
    }

    public void setReservationId(Integer reservationId) {
        this.reservationId = reservationId;
    }

    public String getBookingStatus() {
        return bookingStatus;
    }

    public void setBookingStatus(String bookingStatus) {
        this.bookingStatus = bookingStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
