package com.inlocal.restaurantapp.ui.followers.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class FollowersItem implements Serializable {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("profileimage")
    @Expose
    private String profileImage;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("isFollow")
    @Expose
    private Boolean isFollow;
    @SerializedName("other_user_type")
    @Expose
    private String otherUserType;
    @SerializedName("other_user_id")
    @Expose
    private Integer otherUserId;

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

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public Boolean isFollow() {
        return isFollow;
    }

    public void setFollow(Boolean follow) {
        isFollow = follow;
    }

    public Integer getOtherUserId() {
        return otherUserId;
    }

    public void setOtherUserId(Integer otherUserId) {
        this.otherUserId = otherUserId;
    }

    public String getOtherUserType() {
        return otherUserType;
    }

    public void setOtherUserType(String otherUserType) {
        this.otherUserType = otherUserType;
    }
}
