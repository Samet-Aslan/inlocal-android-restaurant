package com.inlocal.restaurantapp.ui.notifications.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class NotificationsModel {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("read_status")
    @Expose
    private Integer readStatus;
    @SerializedName("redirection_id")
    @Expose
    private Integer redirectionId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("from_id")
    @Expose
    private Integer fromId;
    @SerializedName("to_id")
    @Expose
    private Integer toId;
    @SerializedName("module_type")
    @Expose
    private String moduleType;
    @SerializedName("user_type")
    @Expose
    private String userType;
    @SerializedName("from_image")
    @Expose
    private String fromImage;
    @SerializedName("to_image")
    @Expose
    private String toImage;
    @SerializedName("time")
    @Expose
    private String time;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(Integer readStatus) {
        this.readStatus = readStatus;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Integer getToId() {
        return toId;
    }

    public void setToId(Integer toId) {
        this.toId = toId;
    }

    public String getModuleType() {
        return moduleType;
    }

    public void setModuleType(String moduleType) {
        this.moduleType = moduleType;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getFromImage() {
        return fromImage;
    }

    public void setFromImage(String fromImage) {
        this.fromImage = fromImage;
    }

    public String getToImage() {
        return toImage;
    }

    public void setToImage(String toImage) {
        this.toImage = toImage;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getRedirectionId() {
        return redirectionId;
    }

    public void setRedirectionId(Integer redirectionId) {
        this.redirectionId = redirectionId;
    }
}
