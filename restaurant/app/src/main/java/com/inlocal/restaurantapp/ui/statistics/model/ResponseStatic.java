package com.inlocal.restaurantapp.ui.statistics.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ResponseStatic implements Serializable {
    @SerializedName("sales")
    @Expose
    private Cancelled sales;
    @SerializedName("orders")
    @Expose
    private Cancelled orders;
    @SerializedName("reservation")
    @Expose
    private Cancelled reservation;
    @SerializedName("cancelled")
    @Expose
    private Cancelled cancelled;

    public Cancelled getSales() {
        return sales;
    }

    public void setSales(Cancelled sales) {
        this.sales = sales;
    }

    public Cancelled getOrders() {
        return orders;
    }

    public void setOrders(Cancelled orders) {
        this.orders = orders;
    }

    public Cancelled getReservation() {
        return reservation;
    }

    public void setReservation(Cancelled reservation) {
        this.reservation = reservation;
    }

    public Cancelled getCancelled() {
        return cancelled;
    }

    public void setCancelled(Cancelled cancelled) {
        this.cancelled = cancelled;
    }
}
