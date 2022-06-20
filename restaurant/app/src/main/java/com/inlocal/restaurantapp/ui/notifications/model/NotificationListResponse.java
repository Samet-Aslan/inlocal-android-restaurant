package com.inlocal.restaurantapp.ui.notifications.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class NotificationListResponse implements Serializable {

    @SerializedName("skip")
    @Expose
    private Integer skip;
    @SerializedName("limit")
    @Expose
    private Integer limit;

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

    @SerializedName("total")
    @Expose
    private Integer total;
    @SerializedName("notificationList")
    @Expose
    private List<NotificationsModel> notificationList = null;

    public List<NotificationsModel> getNotificationList() {
        return notificationList;
    }

    public void setNotificationList(List<NotificationsModel> notificationList) {
        this.notificationList = notificationList;
    }
}
