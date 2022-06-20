package com.inlocal.restaurantapp.commonmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class MenuItem implements Serializable {

    private int type;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("menu_category_id")
    @Expose
    private Integer menuCategoryId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("ordered_count")
    @Expose
    private String orderedCount;

    public Integer getId() {
        return id;
    }

    public MenuItem(int type) {
        this.type = type;
    }

    public MenuItem() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMenuCategoryId() {
        return menuCategoryId;
    }

    public void setMenuCategoryId(Integer menuCategoryId) {
        this.menuCategoryId = menuCategoryId;
    }

    public String getOrderedCount() {
        return orderedCount;
    }

    public void setOrderedCount(String orderedCount) {
        this.orderedCount = orderedCount;
    }
}
