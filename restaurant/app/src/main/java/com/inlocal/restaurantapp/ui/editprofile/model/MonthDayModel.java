package com.inlocal.restaurantapp.ui.editprofile.model;

public class MonthDayModel {
    private String name,price;
    private boolean selected;

    public MonthDayModel(String name, boolean selected, String price) {
        this.name = name;
        this.price = price;
    }


    public MonthDayModel(String name, boolean selected) {
        this.name = name;
        this.price = price;
        this.selected = selected;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
