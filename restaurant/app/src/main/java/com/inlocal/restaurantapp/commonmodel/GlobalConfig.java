package com.inlocal.restaurantapp.commonmodel;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class GlobalConfig implements Serializable {
    @SerializedName("notificationCount")
    private Integer notificationCount;

    public Integer getNotificationCount() {
        return notificationCount;
    }

    public void setNotificationCount(Integer notificationCount) {
        this.notificationCount = notificationCount;
    }
}
