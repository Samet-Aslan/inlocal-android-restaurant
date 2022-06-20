package com.inlocal.restaurantapp.ui.homefragments.ui.bookinglist.model;

public class BookingModel {
    private int type;
    private boolean status;

    public BookingModel(int type, boolean status) {
        this.type = type;
        this.status = status;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
