package com.inlocal.restaurantapp.ui.homefragments.ui.bookinglist.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Customer implements Serializable {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("fullname")
    @Expose
    private String fullname;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("profile_image")
    @Expose
    private String profileImage;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("dail_code")
    @Expose
    private Object dailCode;
    @SerializedName("pwd_reset_token")
    @Expose
    private Object pwdResetToken;
    @SerializedName("device_id")
    @Expose
    private String deviceId;
    @SerializedName("device_type")
    @Expose
    private String deviceType;
    @SerializedName("device_token")
    @Expose
    private String deviceToken;
    @SerializedName("cuisine_id")
    @Expose
    private Object cuisineId;
    @SerializedName("phone_verified")
    @Expose
    private String phoneVerified;
    @SerializedName("otp")
    @Expose
    private Object otp;
    @SerializedName("country_code")
    @Expose
    private String countryCode;
    @SerializedName("followers")
    @Expose
    private Integer followers;
    @SerializedName("followings")
    @Expose
    private Integer followings;
    @SerializedName("posts_counter")
    @Expose
    private Integer postsCounter;
    @SerializedName("insight_counter")
    @Expose
    private Integer insightCounter;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("is_default")
    @Expose
    private Integer isDefault;
    @SerializedName("created_by")
    @Expose
    private Object createdBy;
    @SerializedName("updated_by")
    @Expose
    private Object updatedBy;
    @SerializedName("deleted_by")
    @Expose
    private Object deletedBy;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Object getDailCode() {
        return dailCode;
    }

    public void setDailCode(Object dailCode) {
        this.dailCode = dailCode;
    }

    public Object getPwdResetToken() {
        return pwdResetToken;
    }

    public void setPwdResetToken(Object pwdResetToken) {
        this.pwdResetToken = pwdResetToken;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public Object getCuisineId() {
        return cuisineId;
    }

    public void setCuisineId(Object cuisineId) {
        this.cuisineId = cuisineId;
    }

    public String getPhoneVerified() {
        return phoneVerified;
    }

    public void setPhoneVerified(String phoneVerified) {
        this.phoneVerified = phoneVerified;
    }

    public Object getOtp() {
        return otp;
    }

    public void setOtp(Object otp) {
        this.otp = otp;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public Integer getFollowers() {
        return followers;
    }

    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    public Integer getFollowings() {
        return followings;
    }

    public void setFollowings(Integer followings) {
        this.followings = followings;
    }

    public Integer getPostsCounter() {
        return postsCounter;
    }

    public void setPostsCounter(Integer postsCounter) {
        this.postsCounter = postsCounter;
    }

    public Integer getInsightCounter() {
        return insightCounter;
    }

    public void setInsightCounter(Integer insightCounter) {
        this.insightCounter = insightCounter;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(Integer isDefault) {
        this.isDefault = isDefault;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Object getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(Object updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Object getDeletedBy() {
        return deletedBy;
    }

    public void setDeletedBy(Object deletedBy) {
        this.deletedBy = deletedBy;
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

}

