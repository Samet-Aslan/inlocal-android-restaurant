package com.inlocal.restaurantapp.ui.additem.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class CustomizeList implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("menu_item_id")
    @Expose
    private Integer menuItemId;
    @SerializedName("is_multi")
    @Expose
    private String isMulti;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("active")
    @Expose
    private Integer active;
    @SerializedName("menuitemsubaddon")
    @Expose
    private List<CustomizeSubItem> customizeSubItem;

    public CustomizeList(String title, Integer active, List<CustomizeSubItem> customizeSubItem) {
        this.title = title;
        this.active = active;
        this.customizeSubItem = customizeSubItem;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Integer menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getIsMulti() {
        return isMulti;
    }

    public void setIsMulti(String isMulti) {
        this.isMulti = isMulti;
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

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getActive() {
        return active;
    }

    public void setActive(Integer active) {
        this.active = active;
    }

    public List<CustomizeSubItem> getCustomizeSubItem() {
        return customizeSubItem;
    }

    public void setCustomizeSubItem(List<CustomizeSubItem> customizeSubItem) {
        this.customizeSubItem = customizeSubItem;
    }
}
