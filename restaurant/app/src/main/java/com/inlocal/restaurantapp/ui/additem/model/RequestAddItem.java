package com.inlocal.restaurantapp.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.io.Serializable;
import java.util.List;

public class RequestAddItem implements Serializable {
    @SerializedName("category_id")
    @Expose
    private Integer categoryId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private File image;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("EatInsideAvailable")
    @Expose
    private Integer eatInsideAvailable;
    @SerializedName("DeliveryAvailable")
    @Expose
    private Integer deliveryAvailable;
    @SerializedName("customizeList")
    @Expose
    private List<CustomizeList> customizeList;

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public File getImage() {
        return image;
    }

    public void setImage(File image) {
        this.image = image;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getEatInsideAvailable() {
        return eatInsideAvailable;
    }

    public void setEatInsideAvailable(Integer eatInsideAvailable) {
        this.eatInsideAvailable = eatInsideAvailable;
    }

    public Integer getDeliveryAvailable() {
        return deliveryAvailable;
    }

    public void setDeliveryAvailable(Integer deliveryAvailable) {
        this.deliveryAvailable = deliveryAvailable;
    }

    public List<CustomizeList> getCustomizeList() {
        return customizeList;
    }

    public void setCustomizeList(List<CustomizeList> customizeList) {
        this.customizeList = customizeList;
    }
}
