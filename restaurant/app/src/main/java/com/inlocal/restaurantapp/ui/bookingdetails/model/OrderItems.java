package com.inlocal.restaurantapp.ui.bookingdetails.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class OrderItems implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("order_id")
    @Expose
    private Integer orderId;
    @SerializedName("menu_item_id")
    @Expose
    private Integer menuItemId;
    @SerializedName("menu_item_name")
    @Expose
    private String menuItemName;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("price")
    @Expose
    private Double price;
    @SerializedName("qty")
    @Expose
    private Integer qty;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("customizeData")
    @Expose
    private List<String> customizeData = null;
    @SerializedName("orderSubaddonItems")
    @Expose
    private List<OrderSubaddonItem> orderSubaddonItems = null;
    private Double displayCalPrice=0.0;

    public List<OrderSubaddonItem> getOrderSubaddonItems() {
        return orderSubaddonItems;
    }

    public void setOrderSubaddonItems(List<OrderSubaddonItem> orderSubaddonItems) {
        this.orderSubaddonItems = orderSubaddonItems;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Integer menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getMenuItemName() {
        return menuItemName;
    }

    public void setMenuItemName(String menuItemName) {
        this.menuItemName = menuItemName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<String> getCustomizeData() {
        return customizeData;
    }

    public void setCustomizeData(List<String> customizeData) {
        this.customizeData = customizeData;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getDisplayCalPrice() {
        return displayCalPrice;
    }

    public void setDisplayCalPrice(Double displayCalPrice) {
        this.displayCalPrice = displayCalPrice;
    }
}
