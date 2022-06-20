package com.inlocal.restaurantapp.commonmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class StoryItem implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("story_id")
    @Expose
    private Integer storyId;
    @SerializedName("menu_item_id")
    @Expose
    private Integer menuItemId;
    @SerializedName("restaurant_id")
    @Expose
    private Integer restaurantId;
    @SerializedName("restaurant_img")
    @Expose
    private String restaurantImg;
    @SerializedName("menu_image")
    @Expose
    private String menuImage;
    @SerializedName("menu_name")
    @Expose
    private String menu_name;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("story_user_type")
    @Expose
    private String storyUserType;
    @SerializedName("user_story_by")
    @Expose
    private Integer userStoryBy;
    @SerializedName("like_counter")
    @Expose
    private Integer likeCounter;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("story_image")
    @Expose
    private String storyImage;
    @SerializedName("active")
    @Expose
    private String active;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getStoryImage() {
        return storyImage;
    }

    public void setStoryImage(String storyImage) {
        this.storyImage = storyImage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getStoryUserType() {
        return storyUserType;
    }

    public void setStoryUserType(String storyUserType) {
        this.storyUserType = storyUserType;
    }

    public Integer getUserStoryBy() {
        return userStoryBy;
    }

    public void setUserStoryBy(Integer userStoryBy) {
        this.userStoryBy = userStoryBy;
    }

    public Integer getStoryId() {
        return storyId;
    }

    public void setStoryId(Integer storyId) {
        this.storyId = storyId;
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

    public String getMenu_name() {
        return menu_name;
    }

    public void setMenu_name(String menu_name) {
        this.menu_name = menu_name;
    }

    public Integer getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(Integer restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getRestaurantImg() {
        return restaurantImg;
    }

    public void setRestaurantImg(String restaurantImg) {
        this.restaurantImg = restaurantImg;
    }

    public String getMenuImage() {
        return menuImage;
    }

    public void setMenuImage(String menuImage) {
        this.menuImage = menuImage;
    }
}
