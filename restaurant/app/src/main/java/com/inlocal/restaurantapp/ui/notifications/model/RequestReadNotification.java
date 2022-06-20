package com.inlocal.restaurantapp.ui.notifications.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RequestReadNotification implements Serializable {
    @SerializedName("login_user_type")
    @Expose
    private String loginUserType;
    @SerializedName("notification_id")
    @Expose
    private Integer notificationId;

    public RequestReadNotification(String loginUserType, Integer notificationId) {
        this.loginUserType = loginUserType;
        this.notificationId = notificationId;
    }

    public String getLoginUserType() {
        return loginUserType;
    }

    public void setLoginUserType(String loginUserType) {
        this.loginUserType = loginUserType;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }
}
