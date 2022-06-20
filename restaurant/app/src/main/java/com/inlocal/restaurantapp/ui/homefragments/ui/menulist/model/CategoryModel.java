package com.inlocal.restaurantapp.ui.homefragments.ui.menulist.model;

public class CategoryModel {
    private int type;
    private int id;
    private String name;
    private boolean isSelected;
    public static final int VIEW_TYPE_ADD = 1;
    public static final int VIEW_TYPE_ITEM = 2;
/*
    public CategoryModel(int type){
        this.type=type;
    }*/

    public CategoryModel(int id, String name,boolean isSelected, int type) {
        this.id = id;
        this.name = name;
        this.isSelected = isSelected;
        this.type=type;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
