package com.inlocal.restaurantapp.ui.additem.model.menudetailsresponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.inlocal.restaurantapp.ui.additem.model.CustomizeList;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;

import java.io.Serializable;
import java.util.List;

public class MenuItemDetails implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("EatInsideAvailable")
    @Expose
    private String eatInsideAvailable;
    @SerializedName("DeliveryAvailable")
    @Expose
    private String deliveryAvailable;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("customizeList")
    @Expose
    private List<CustomizeList> customizeList = null;
    @SerializedName("PostList")
    @Expose
    private List<FeedWallItem> postList = null;

    public Integer getId() {
        return id;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPrice() {
        if(price==null){
            return "";
        }else {
            return price;
        }
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getEatInsideAvailable() {
        return eatInsideAvailable;
    }

    public void setEatInsideAvailable(String eatInsideAvailable) {
        this.eatInsideAvailable = eatInsideAvailable;
    }

    public String getDeliveryAvailable() {
        return deliveryAvailable;
    }

    public void setDeliveryAvailable(String deliveryAvailable) {
        this.deliveryAvailable = deliveryAvailable;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public List<CustomizeList> getCustomizeList() {
        return customizeList;
    }

    public void setCustomizeList(List<CustomizeList> customizeList) {
        this.customizeList = customizeList;
    }

    public List<FeedWallItem> getPostList() {
        return postList;
    }

    public void setPostList(List<FeedWallItem> postList) {
        this.postList = postList;
    }
}
