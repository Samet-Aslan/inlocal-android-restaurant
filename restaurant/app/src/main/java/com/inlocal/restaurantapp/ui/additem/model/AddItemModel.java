package com.inlocal.restaurantapp.ui.additem.model;

import com.inlocal.restaurantapp.ui.editprofile.model.MonthDayModel;

import java.util.ArrayList;

public class AddItemModel {
    private String name;
    private ArrayList<MonthDayModel> types;

    public AddItemModel(String name, ArrayList<MonthDayModel> types) {
        this.name = name;
        this.types = types;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<MonthDayModel> getTypes() {
        return types;
    }

    public void setTypes(ArrayList<MonthDayModel> types) {
        this.types = types;
    }
}
