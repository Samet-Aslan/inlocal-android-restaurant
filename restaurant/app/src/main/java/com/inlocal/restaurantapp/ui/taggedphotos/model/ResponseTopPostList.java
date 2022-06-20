package com.inlocal.restaurantapp.ui.taggedphotos.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.inlocal.restaurantapp.ui.homefragments.ui.home.model.FeedWallItem;

import java.io.Serializable;
import java.util.List;

public class ResponseTopPostList implements Serializable {

    @SerializedName("skip")
    @Expose
    private Integer skip;
    @SerializedName("limit")
    @Expose
    private Integer limit;
    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("post_id")
    @Expose
    private Integer postId;
    @SerializedName("user_post_by")
    @Expose
    private Integer userPostBy;
    @SerializedName("restaurant_id")
    @Expose
    private Integer restaurantId;
    @SerializedName("menu_item_id")
    @Expose
    private Integer menuItemId;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("post_user_type")
    @Expose
    private String postUserType;
    @SerializedName("like_counter")
    @Expose
    private Integer likeCounter;
    @SerializedName("post_image")
    @Expose
    private String postImage;
    @SerializedName("restaurant_img")
    @Expose
    private String restauranImg;
    @SerializedName("active")
    @Expose
    private String active;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("isLiked")
    @Expose
    private Boolean isLiked;
    @SerializedName("isFavorite")
    @Expose
    private Boolean isFavorite;
    @SerializedName("PostList")
    @Expose
    private List<FeedWallItem> postList;

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
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

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
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

    public Boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(Boolean favorite) {
        isFavorite = favorite;
    }

    public Boolean getLiked() {
        return isLiked;
    }

    public void setLiked(Boolean liked) {
        isLiked = liked;
    }

    public String getRestauranImg() {
        return restauranImg;
    }

    public void setRestauranImg(String restauranImg) {
        this.restauranImg = restauranImg;
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

    public List<FeedWallItem> getPostList() {
        return postList;
    }

    public void setPostList(List<FeedWallItem> postList) {
        this.postList = postList;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
