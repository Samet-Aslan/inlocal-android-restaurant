package com.inlocal.restaurantapp.commonmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostItem implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("restaurant_id")
    @Expose
    private Integer restaurantId;
    @SerializedName("user_post_by")
    @Expose
    private Integer userPostBy;
    @SerializedName("menu_item_id")
    @Expose
    private Integer menuItemId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("like_counter")
    @Expose
    private Integer likeCounter;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("post_user_type")
    @Expose
    private String postUserType;
    @SerializedName("active")
    @Expose
    private String active;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Integer getMenuItemId() {
        return menuItemId;
    }

    public void setMenuItemId(Integer menuItemId) {
        this.menuItemId = menuItemId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getLikeCounter() {
        return likeCounter;
    }

    public void setLikeCounter(Integer likeCounter) {
        this.likeCounter = likeCounter;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getPostUserType() {
        return postUserType;
    }

    public void setPostUserType(String postUserType) {
        this.postUserType = postUserType;
    }

    public Integer getUserPostBy() {
        return userPostBy;
    }

    public void setUserPostBy(Integer userPostBy) {
        this.userPostBy = userPostBy;
    }
}
